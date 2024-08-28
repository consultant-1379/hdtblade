#
# db_loader config file.
#
# Modify variables below.
#
import platform;
from sys import exit;

#
# MySQL configuration
#
HOSTNAME = "localhost";
USERNAME = "root";
PASSWORD = "wireless";
DATABASE = "hdt";

#
# Various files and directories.
#
sys_str =  platform.system();
if sys_str == "Windows":	
	# Specify the directory where the JavaScript plugin files are located.
	JSFILES_DIRECTORY = "C:\\Users\\escralp\\Documents\\NetBeansProjects\\HDT\\hdt_scripts\\formula_js" 
	
	# Specify the input directory which contains the Excel files.	
	EXCELFILES_DIRECTORY="C:\\Users\\escralp\\Documents\\NetBeansProjects\\HDT\\db\\xls"
	
	# Specify the directory where to store CSV files.
	CSVFILES_DIRECTORY="C:\\Users\\escralp\\Documents\\NetBeansProjects\\HDT\\db\\csv"
	
	# Same as above but must be relative to the machine running the MySQL server.
	CSVFILES_DIRECTORY_DB="C:\\Users\\escralp\\Documents\\NetBeansProjects\\HDT\\db\\csv"
	
	# Location of the HDT schema file. 
	DBSCHEMA_FILE="C:\\Users\\escralp\\Documents\\NetBeansProjects\\HDT\\db\\hdt_schema.sql"
	
	# Some static content.
	HDT_CONFIG_TBL_QUERY_CLEAR = "DELETE FROM `hdtconfiguration`"
	HDT_CONFIG_TBL_QUERY = "INSERT INTO `hdtconfiguration` (`dbversion`) VALUES ('1.0PA7DVL1')"
	
	# Command to show all tables.
	ALL_TABLES_QRY = "SHOW TABLES IN `hdt`";	
elif sys_str == "Darwin":
	# Mac OS related settings.
	HDT_DATA_BASE_DIR="/Users/ralphschlosser/Develop/NetbeansProjects/HDT/"

	# Specify the directory where the JavaScript plugin files are located.
	JSFILES_DIRECTORY = HDT_DATA_BASE_DIR + "hdt_scripts/formula_js" 
	
	# Specify the input directory which contains the Excel files.
	EXCELFILES_DIRECTORY = HDT_DATA_BASE_DIR + "db/xls"
	
	# Specify the directory where to store CSV files.
	CSVFILES_DIRECTORY = HDT_DATA_BASE_DIR + "db/csv"
	
	# Same as above but must be relative to the machine running the MySQL server.
	CSVFILES_DIRECTORY_DB = HDT_DATA_BASE_DIR + "db/csv"
	
	# Location of the HDT schema file. 
	DBSCHEMA_FILE = HDT_DATA_BASE_DIR + "db/hdt_schema.sql"
	
	# Some static content.
	HDT_CONFIG_TBL_QUERY_CLEAR = "DELETE FROM `hdtconfiguration`"
	HDT_CONFIG_TBL_QUERY = "INSERT INTO `hdtconfiguration` (`dbversion`) VALUES ('1.0PA7DVL1')"
	
	# Command to show all tables.
	ALL_TABLES_QRY = "SHOW TABLES IN `hdt`";	
elif sys_str == "Linux":
	print "Linux unsupported / untested.";
	sys.exit();
else:
	print "Could not determine type of system. Aborting.";
	sys.exit(-1)
