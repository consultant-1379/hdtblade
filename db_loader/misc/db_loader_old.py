# Quick DB loader script in Python
#
# This utility script prepares the DB for the HDT.
#
# Author: Ralph Schlosser (escralp)
# Date: 10/12/2010
# Version: 0.1

import sys
import os
import stat
import traceback
import MySQLdb
from time import sleep
from GUI import ModalDialog, Label, Button, Task, application 

import db_loader_config

# Some constants.
APPNAME = "db_loader";
VERSION = "Version 0.1";
SLEEP_VAL = 2;

class InfoWindow(ModalDialog):
    
    def __init__(self, text, timeout):
        ModalDialog.__init__(self);
        label = Label(text);
        self.ok_button = Button("OK", action = "ok", enabled = True);
        self.cancel_button = Button("Cancel", action = "cancel", enabled = True);	self.place(label, left = 20, top = 20);
        self.place(self.ok_button, top = label + 20, right = label.right - 40);
        self.place(self.cancel_button, top = label + 20, right = label.right + 40);	
        self.shrink_wrap(padding = (20, 20));
        #self.timer = Task(self.enable_button, timeout);
    
    #def enable_button(self):
    #    self.ok_button.enabled = True;
    
    def ok(self):
        self.dismiss(True);
    def cancel(self):
	self.dismiss(False);

def load_db():	
	print "Loading tables into database...";
	db = MySQLdb.connect(db_loader_config.HOSTNAME, db_loader_config.USERNAME, db_loader_config.PASSWORD, db_loader_config.DATABASE);	
	db.close();
	print "\nSleeping " + str(SLEEP_VAL) + " seconds...";	
	sleep(SLEEP_VAL);
	
def load_formulas():
	print "Loading Formulas from JavaScript files...";
	# Read all the files.
	filelist = os.listdir(db_loader_config.JSFILES_DIRECTORY);
        # Open a connection to the database.
	db = MySQLdb.connect(db_loader_config.HOSTNAME, db_loader_config.USERNAME, db_loader_config.PASSWORD, db_loader_config.DATABASE);
	
	# Delete all existing formulas.
	print "Deleting existing formulas."
	sql = 'DELETE FROM formulas';
	cursor = db.cursor();
	cursor.execute(sql);
	
	# Now add the JavaScript code contained in each file to the DB.
	for filename in filelist:
		path = db_loader_config.JSFILES_DIRECTORY + "/" + filename;
		# Determine the status of the file.
		statinfo = os.stat(path);
		ftype = stat.S_IFMT(statinfo.st_mode);		
		if stat.S_ISDIR(ftype) == True:
			print "Not considering " + path + " because it is a directory.";
		else:
			if (filename.split(".")[1] == "js"):
				print "Processing formula file " + path;		
				# Read the JS file into a buffer.
				file = open(path);
				filebuffer = file.read();		
				# This may be stupid.
				formula_name = filename.split(".")[0].upper();									
				# Build the SQL query string.
				sql = 'INSERT INTO `formulas` (`formula_name`, `formula_description`, `formula_js_code`) VALUES ("' + formula_name + '", "Automatic Formula", ' + '"' + filebuffer + '");';
				print "Executing query: \n" + sql + "\n";
				cursor = db.cursor();
				cursor.execute(sql);
			else:
				print "Not considering " + path + " due to invalid file ending.";
        
	db.commit();		
	db.close();
	print "\nSleeping " + str(SLEEP_VAL) + " seconds...";	
	sleep(SLEEP_VAL);

def main():
	print APPNAME + " " + VERSION;
	dlog = InfoWindow("Load HDT database?", 10);
	result = dlog.present();
	if result == True:	
		try:
                    load_db();			
                    load_formulas();
		except:
                    type, value, sys.last_traceback = sys.exc_info();
                    lines = traceback.format_exception(type, value,sys.last_traceback);		
                    except_dlog = InfoWindow("Exception:\n\n" + "".join(lines), 0);
                    except_dlog.present();
	else:
            print "Aborted."
	
if __name__ == '__main__':	
    main();
