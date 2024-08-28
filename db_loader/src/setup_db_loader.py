#
# Needed to create .exe file for db_loader.
# RS 10/05/2011
#
# Usage: python setup_db_loader.py py2exe

from distutils.core import setup
import py2exe

# NOTE: The "platform" dependency comes from the config file imported. 
setup(console = ['db_loader.py'],
	options = {
		"py2exe": {
			"optimize": 2,
            "includes": ['platform']
		}
});
