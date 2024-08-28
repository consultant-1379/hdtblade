//ENIQS MEM Calculation

var A_MEM
//ar ENIQS_Counter_Millions

A_MEM =2.962* PM_COUNT +29.613

if (A_MEM > 1 && A_MEM <= 8) {
	ENIQS_MEM = 96;
} 
else if (A_MEM > 8 && A_MEM <= 16) {
	ENIQS_MEM = 96;
} 
else if (A_MEM > 16 && A_MEM <= 32) {
	ENIQS_MEM = 96;
} 
else if (A_MEM > 32 && A_MEM <= 64) {
	ENIQS_MEM = 96;
} 
else if (A_MEM > 64 && A_MEM <= 96) {
	ENIQS_MEM = 96;
} 
else if (A_MEM > 97 && A_MEM <= 128) {
	ENIQS_MEM = 96;
} 
else if (A_MEM > 128 && A_MEM <= 160) {
	ENIQS_MEM = 96;
} 
else if (A_MEM > 160 && A_MEM <= 192) {
	ENIQS_MEM = 256;
} 
else if (A_MEM > 192 && A_MEM <= 224) {
	ENIQS_MEM = 256;
} 
else if (A_MEM > 224 && A_MEM <= 350) {
	ENIQS_MEM = 256;
} else {
	ENIQS_MEM = -1;
}