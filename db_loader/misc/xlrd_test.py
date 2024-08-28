import xlrd
import csv

# Open workbook.
workbook = xlrd.open_workbook('C:\\Users\\escralp\\Documents\\NetBeansProjects\\HDT\\db\\xls\\parameter_values.xls')
 
# Get the first sheet by index.
sheet = workbook.sheet_by_index(0)

csv_fh = open('C:\\Users\\escralp\\Documents\\NetBeansProjects\\HDT\\db\\csv\\parameter_values.csv','w')
csv_wrt = csv.writer(csv_fh, lineterminator="\n");

for row in range(sheet.nrows):
    this_row = [];
    for col in range(sheet.ncols):
		val = sheet.cell_value(row, col);
		if isinstance(val, unicode):
			val = val.encode('utf8');		
		this_row.append(val);	
    csv_wrt.writerow(this_row);

csv_fh.close();