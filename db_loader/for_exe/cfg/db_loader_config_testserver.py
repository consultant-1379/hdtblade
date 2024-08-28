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
HOSTNAME = "eiebhvh006.eei.ericsson.se";
USERNAME = "root";
PASSWORD = "not124get";
DATABASE = "hdt_test";
#
# Various files and directories.
#
sys_str =  platform.system();
if sys_str == "Windows":	
	# Specify the directory where the JavaScript plugin files are located.
	JSFILES_DIRECTORY = "Z:\\hdt_install\\test\\formula_js" 
	
	# Specify the input directory which contains the Excel files.
	EXCELFILES_DIRECTORY="Z:\\hdt_install\\test\\db"
	
	# Specify the directory where to store CSV files.
 	CSVFILES_DIRECTORY="Z:\\hdt_install\\test\\csv"
	
	# Same as above but must be relative to the machine running the MySQL server.
	CSVFILES_DIRECTORY_DB="E:\\Temp\\hdt_install\\test\\csv"
	
	# Location of the HDT schema file. 
	DBSCHEMA_FILE="..\hdt_schema_testserver.sql"
	
	# Some static content.
	HDT_CONFIG_TBL_QUERY_CLEAR = "DELETE FROM `hdtconfiguration`"
	HDT_CONFIG_TBL_QUERY = "INSERT INTO `hdtconfiguration` (`hdtversion`, `dbversion`) VALUES ('0.1 DEVEL', 'db_ver_01')"
	
	# Command to show all tables.
	ALL_TABLES_QRY = "SHOW TABLES IN `hdt_test`";
	
elif sys_str == "Linux":
	print "Linux unsupported / untested.";
	sys.exit();
else:
	print "Could not determine type of system. Aborting.";
	sys.exit(-1)
