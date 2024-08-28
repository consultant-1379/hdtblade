def main():
	app_buf = [];
	in_file = open("app_data.sql");
	s = in_file.read();
	print s;
	i = 0;
	for line in in_file:
		if (line != "\n"):			
			if (line  not in app_buf):
				app_buf.append(line);
				print line;
				i = i + 1;
	
	in_file.close();
if __name__ == '__main__':	
	main();
