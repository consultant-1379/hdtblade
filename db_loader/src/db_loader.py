# Quick DB loader script in Python
#
# This utility script prepares the DB for the HDT.
# Authors: Ralph Schlosser (escralp), Santosh Budhe (esantbu)
# Date: 10/12/2010
# Last changed: 12/05/2011
#
# FIXME: Needs some serious refactoring.

import sys
import codecs
import os
import stat
import traceback
import platform
import MySQLdb
import xlrd
import csv
from time import sleep
import imp
import fileinput

# Some "constants".
APPNAME = "db_loader";
VERSION = "Version 0.25";
SLEEP_VAL = 2;
PLATFORM = platform.system();


#
# Converts Excel to CSV files.
#
def convert_excel_to_csv(configfile):
	print "Converting Excel files into CSV...";

	# Read all the Excel Files.
	try:
		filelist = os.listdir(configfile.EXCELFILES_DIRECTORY);
	except:
		print "\nError while retrieving the list of Excel files from " + configfile.EXCELFILES_DIRECTORY + "\n\n";
		print sys.exc_info();
		sys.exit();
		
	# Create list of the existing tables.
	try:
		db = MySQLdb.connect(configfile.HOSTNAME, configfile.USERNAME, configfile.PASSWORD);
		cursor = db.cursor();		 
		cursor.execute(configfile.ALL_TABLES_QRY);
		rows = cursor.fetchall();
		db.close();
	except:
		print "\nError while retrieving the list of tables from the HDT database.\n\n";
		print sys.exc_info();
		sys.exit();

	# FIXME: Delete all existing CSV files in the target directory.
	
	# Take each excel file and create a CSV file.	 
	for filename in filelist:					  
		for row in rows:
			for column in row:							  
				if (filename.split(".")[0] == column): # Checking if the table exists for the xls sheet						   
					# Check if the filename is present in the list of table
					# FIXME: This is all shit. Just a quick hack to get it to work on MacOS X
					if PLATFORM == "Windows":
						path = configfile.EXCELFILES_DIRECTORY + "\\" + filename;
					else:
						path = configfile.EXCELFILES_DIRECTORY + "/" + filename;
					# Determine the status of the file.
					try:
						statinfo = os.stat(path);
						ftype = stat.S_IFMT(statinfo.st_mode);		
						if stat.S_ISDIR(ftype) == True:										   
							# print "Not considering " + path + " because it is a directory.";
							continue;
						else:
							if (filename.split(".")[1] == "xls"):
								print "Processing Excel file " + path;
								# Open workbook.
								workbook = xlrd.open_workbook(path) 
								# Get the first sheet by index.
								sheet = workbook.sheet_by_index(0)
								excelSheet_name = filename.split(".")[0];
								csvfilename = excelSheet_name + ".csv";								   
								
								if PLATFORM == "Windows": 
									csv_path = configfile.CSVFILES_DIRECTORY + "\\" + csvfilename;
								else:
									csv_path = configfile.CSVFILES_DIRECTORY + "/" + csvfilename;

								csv_fh = open(csv_path, "w");
								csv_wrt = csv.writer(csv_fh, lineterminator = "\n");
								# Generate the CSV file
								for row in range(sheet.nrows):
									this_row = [];
									for col in range(sheet.ncols):
										val = sheet.cell_value(row, col);
										if isinstance(val, unicode):
											val = val.encode('utf8');		
										this_row.append(val);	
									csv_wrt.writerow(this_row);
								
								csv_fh.close();																
								print "Wrote CSV file " + csv_path;								   
					except:
						print "\nError while writing CSV output file to " + csv_path + "\n\n";
						print sys.exc_info();
						sys.exit();							   
		
# Checks only if it is utf8.				
def autodecode(firstlineofthefile):
	try:
		if firstlineofthefile.startswith(codecs.BOM_UTF8):
			# The byte string s is UTF-8			
			out = firstlineofthefile.decode("utf8");
			return out[1:];
		else:			 
			return firstlineofthefile;
	except:
		 print "\nError in autodecode().\n";
		 print sys.exc_info();
		 sys.exit();
		
def create_db(configfile):
	print "\nCreating HDT database...\n"		
	try:	  
		# Open a connection to the database.
		db = MySQLdb.connect(configfile.HOSTNAME, configfile.USERNAME, configfile.PASSWORD);
		cursor = db.cursor();
		#cursor.connection.autocommit(True);
		query = "";

		for sline in fileinput.input(configfile.DBSCHEMA_FILE):			  
			# Remove encoding byte.
			sline = autodecode(sline);
			sline = sline.rstrip();

			incomment = False;
			insideQuery = False;
			if (sline.startswith("--") or len(sline.rstrip()) == 0):
			   # SQL Comment line, skip				  
			   continue;

			if (sline.rstrip() == "/*"):
			   # start of SQL comment block
			   incomment = True				 
			   continue;
			
			if (sline.rstrip() == "*/"):			
				incomment = False;
				continue;
			
			if (not incomment and sline.find(";") !=- 1):	 #line has a semi colon it means it is the end of the query.			   
			   query = query + " " + sline ;			  
			   cursor = db.cursor();
			   cursor.execute(query);
			   cursor.close();
			   query = ""
			   
			if (not incomment and  sline.find(";") == -1) :	   #if line does not have a semi colon that means there is more to it						 
			   query = query + " " + sline;
			   
		db.commit();
		db.close();
		print "\nDone. Sleeping " + str(SLEEP_VAL) + " seconds..."; 
		sleep(SLEEP_VAL); 
	except:
		print "\nError in creating HDT tables.\n\n";
		print sys.exc_info();
		db.close();
		sys.exit();	   

def populate_tables(configfile):
	print "\nPopulating the tables with data...\n";
	try:
		# FIXME: This makes it break in Linux.
		
		csv_file_dir_qry = configfile.CSVFILES_DIRECTORY_DB;
		
		if PLATFORM == "Windows":
			csv_file_dir_qry = csv_file_dir_qry.replace("\\", "\\\\");
			
		# Read all the files.
		filelist = os.listdir(configfile.CSVFILES_DIRECTORY);
		# Open a connection to the database.
		db = MySQLdb.connect(configfile.HOSTNAME, configfile.USERNAME, configfile.PASSWORD, configfile.DATABASE);
		# Now add the JavaScript code contained in each file to the DB.
		for filename in filelist:					 
			path = configfile.CSVFILES_DIRECTORY + "/" + filename;
			# Determine the status of the file.
			statinfo = os.stat(path);
			ftype = stat.S_IFMT(statinfo.st_mode);		
			if stat.S_ISDIR(ftype) == True:
				# print "Not considering " + path + " because it is a directory.";
				continue;
			else:
				if (filename.split(".")[1] == "csv"):
					table_name = filename.split(".")[0];
					# Need 4 backslashes for MySQL!
					# FIXME: Also makes it break in Linux.
					if PLATFORM == "Windows":
						complete_table_path = "'" + csv_file_dir_qry + "\\\\" + filename + "'";
					else:
						complete_table_path = "'" + csv_file_dir_qry + "/" + filename + "'";
						
					# Build the SQL query string.
					cleartables = "DELETE FROM " + table_name;
					
					if PLATFORM == "Windows":
						loaddata = "LOAD DATA INFILE " + complete_table_path + " INTO TABLE `" + table_name + "` FIELDS TERMINATED BY \',\' OPTIONALLY ENCLOSED BY	\'\"\'	LINES TERMINATED BY \'\\r\\n\' IGNORE 1 LINES";
					else:
						loaddata = "LOAD DATA INFILE " + complete_table_path + " INTO TABLE `" + table_name + "` FIELDS TERMINATED BY \',\' OPTIONALLY ENCLOSED BY	\'\"\'	LINES TERMINATED BY \'\\n\' IGNORE 1 LINES";
						
					print "LOAD DATA Query: " + loaddata;
					cursor = db.cursor();
					cursor.execute(cleartables);
					cursor.execute(loaddata);
					cursor.close();
				else:
					# print "Not considering " + path + " due to invalid file ending.";
					continue;
		filename = "";		 
		# Load the static content.
		cursor = db.cursor();
		cursor.execute(configfile.HDT_CONFIG_TBL_QUERY_CLEAR);
		cursor.execute(configfile.HDT_CONFIG_TBL_QUERY);
		db.commit();
		db.close();
		print "\nDone. Sleeping " + str(SLEEP_VAL) + " seconds..."; 
		sleep(SLEEP_VAL);
	except:
		print "\nError populating the tables.";
		if filename != "":
			print "Problem with table file name: " + filename + "\n\n";
			if (loaddata != ""):
				print "Offending query: " + loaddata;
		else:
			print "Problem loading static content.\n\n";
		print sys.exc_info();
		db.close();
		sys.exit()
		
	
def load_formulas(configfile):
	print "\nLoading formulas.\n"
	try:		
		# Read all the files.
		filelist = os.listdir(configfile.JSFILES_DIRECTORY);
		# Open a connection to the database.
		db = MySQLdb.connect(configfile.HOSTNAME, configfile.USERNAME, configfile.PASSWORD, configfile.DATABASE);
		
		# Delete all existing formulas.		   
		sql = 'DELETE FROM formulas';
		cursor = db.cursor();
		cursor.execute(sql);
		
		# Now add the JavaScript code contained in each file to the DB.
		for filename in filelist:
			path = configfile.JSFILES_DIRECTORY + "/" + filename;					 
			statinfo = os.stat(path);
			ftype = stat.S_IFMT(statinfo.st_mode);		
			if stat.S_ISDIR(ftype) == True:
				# print "Not considering " + path + " because it is a directory.";
				continue;
			else:
				ll = filename.split(".");
				if (len(ll) == 2 and ll[1] == "js"):
					print "Processing formula file " + path;		
					# Read the JS file into a buffer.
					file = open(path);
					filebuffer = file.read();		
					# This may be stupid.
					formula_name = filename.split(".")[0].upper();									
					# Build the SQL query string.
					sql = 'INSERT INTO `formulas` (`formula_name`, `formula_description`, `formula_js_code`) VALUES ("' + formula_name + '", "Automatic Formula", ' + '"' + filebuffer + '");';					   
					cursor = db.cursor();
					cursor.execute(sql);
				else:
					# print "Not considering " + path + " due to invalid file ending.";
					continue;		 
		db.commit();		
		db.close();	   
	except:
		 print "\nError while loading formulas.\n\n"
		 print sys.exc_info();
		 sys.exit()

def usage():
	print "\nCommand line error.\n";
	print "Usage: db_loader.py config file <operation>\n";
	print "<operation> = {createdb, convertxls, loadtable, loadformulas, all}. Default is \"all\".";
	sys.exit();	   

def main(argv):
	global PLATFORM;
	
	print APPNAME + " " + VERSION;
	print "Running on " + PLATFORM;

	# FIXME: Could use getopt for greater flexibility.
	argc = len(argv);		
	if (argc < 2 or argc > 3):
		usage();   
	else:
		configfile = argv[1];
		if (argc == 3):
			operation = argv[2];
		else:
			operation = "all";
	
	print "\nConfig File: " + configfile;
	print "Operation: " + operation +"\n";	

	# Read in the config file dynamically and make its contents accessible through the configfile object.
	configfile = imp.load_source("configfile", configfile);

	# Should be extended so that more than one option can be supplied on the command line.		  
	if operation == "all":
		operations = ["createdb", "convertxls", "loadtable", "loadformulas"];
	elif operation == "loadtable":
		# Need to do both ops for this command to be useful.
		operations = ["convertxls", "loadtable"];
	else:
		operations = [operation];

	for op in operations:		   
		if (op == "createdb"):
			create_db(configfile);			  
		elif (op == "convertxls"):	 
			convert_excel_to_csv(configfile) #esantbu,	convert the excel sheets into CSV
		elif (op == "loadtable"):			   
			populate_tables(configfile);
		elif (op == "loadformulas"): 
			load_formulas(configfile);
		else:
			print "Unknown operation: " + operation
			usage();				

		print "\ndb_loader run complete.\n"
	
if __name__ == '__main__':	
	main(sys.argv);
