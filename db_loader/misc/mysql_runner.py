#!/usr/bin/env /opt/jython-2.2.1/jython
import sys
import getopt
import os.path
import traceback
from  import zxJDBC

class SqlRunner:
  """
  Class to run the SQL and print it out into the output file as a CSV
  """
  def __init__(self, dbconfig, sqlfile, outputfile, sepchar):
    """
    @param dbconfig the database configuration file name
    @param sqlfile the name of the file containing the SQL to be executed
    @param outputfile the name of the file where output will be written
    @param sepchar the separator character (or string) to use
    """
    self.dbconfig = dbconfig
    self.sqlfile = sqlfile
    self.outputfile = outputfile
    self.sepchar = sepchar

  def getDbProps(self):
    """
    Return the database properties as a map of name value pairs.
    @return a map of name value pairs
    """
    if (not os.path.exists(self.dbconfig)):
      raise Exception("File not found: %s" % (self.dbconfig))
    props = {}
    pfile = open(self.dbconfig, 'rb')
    for pline in pfile:
      (name, value) = pline[:-1].split("=")
      props[name] = value
    pfile.close()
    return props

  def getSql(self):
    """
    Validate the sql file name, and parse out the SQL to be run. The
    method will skip SQL single-line comments. Blocks enclosed by free
    standing multi-line comments are also skipped.
    @return the SQL as a string
    """
    if (not os.path.exists(self.sqlfile)):
      raise Exception("File not found: %s" % (self.sqlfile))
    sql = []
    sfile = open(self.sqlfile, 'rb')
    incomment = False
    for sline in sfile:
      sline = sline.rstrip('\n')
      if (sline.startswith("--") or len(sline.rstrip()) == 0):
        # SQL Comment line, skip
        continue
      if (sline.rstrip() == "/*"):
        # start of SQL comment block
        incomment = True
        continue
      if (sline.rstrip() == "*/"):
        # end of SQL comment block
        incomment = False
        continue
      if (not incomment):
        sql.append(sline)
    sfile.close()
    return " ".join(sql)

  def runSql(self):
    """
    Runs the SQL and prints it out into the specified output file as a CSV
    file delimited by sepchar.
    """
    props = self.getDbProps()
    sql = self.getSql()
    print "Running SQL: %s" % (sql)
    ofile = open(self.outputfile, 'wb')
    db = zxJDBC.connect(props["url"], props["user"], props["password"],
      props["driver"])
    cur = db.cursor(True)
    cur.execute(sql)
    # print the header
    meta = cur.description
    print "Writing output to: %s" % (self.outputfile)
    ofile.write(self.sepchar.join(map(lambda x: x[0], meta)) + "\n")
    for row in cur.fetchall():
      strrow = map(lambda x: str(x), row)
      ofile.write(self.sepchar.join(strrow) + "\n")
    ofile.close()
    cur.close()
    db.close()
    
def usage(error=""):
  """
  Print the usage information. If an error message is supplied, print that
  on top of the usage information.
  """
  if (len(str(error)) > 0):
    print "ERROR: %s" % (error)
    print "STACK TRACE:"
    traceback.print_exc()
  print "USAGE:"
  print "%s -d dbconfig -q queryfile -s sepchar -o outputfile" % (sys.argv[0])
  print "OR: %s -h" % (sys.argv[0])
  print "OPTIONS:"
  print "--dbconfig | -d  : database configuration file"
  print "  configuration file must be in properties format, with the following"
  print "  keys defined: driver, url, user and password"
  print "--queryfile | -q : name of file containing SQL to be run"
  print "--outputfile | -o: name of file where results should be written"
  print "--sep | -s       : the separator character to use in output"
  print "--help | -h      : print this information"
  sys.exit(2)

def extractOptions(argv):
  """
  Extract command line options and return a tuple
  @param argv the sys.argv object
  @return a tuple containing the information for running the SQL
  """
  try:
    (opts, args) = getopt.getopt(argv[1:], "d:q:s:o:h",
      ["dbconfig=", "queryfile=", "sep=", "outputfile=", "help"])
  except getopt.GetoptError:
    usage()
  if (len(filter(lambda x: x[0] in ("-h", "--help"), opts)) == 1):
    usage()
  if (len(opts) != 4):
    usage()
  for opt in opts:
    (key, value) = opt
    if (key in ("-d", "--dbconfig")):
      dbconfig = value
    elif (key in ("-q", "--queryfile")):
      sqlfile = value
    elif (key in ("-o", "--outputfile")):
      outputfile = value
    elif (key in ("-s", "--sep")):
      sepchar = value
    else:
      usage()
  return (dbconfig, sqlfile, outputfile, sepchar)
  
def main():
  """
  This is how we are called
  """
  (dbconfig, sqlfile, outputfile, sepchar) = extractOptions(sys.argv)
  sqlrunner = SqlRunner(dbconfig, sqlfile, outputfile, sepchar)
  try:
    sqlrunner.runSql()
  except Exception, e:
    usage(e)

if __name__ == "__main__":
  main()
