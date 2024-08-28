//ENIQS CPU Calculation

var A_CPU
//var ENIQS_Counter_Millions

A_CPU =0.8658*PM_COUNT+6.4314


if (A_CPU>1&&A_CPU<9) {
	ENIQS_CPU = 1;
} 
else if (A_CPU>9&&A_CPU<12) {
	ENIQS_CPU = 2;
} 
else if (A_CPU>12&&A_CPU<48) {
	ENIQS_CPU = 3;
} 
else if (A_CPU>48&&A_CPU<96) {
	ENIQS_CPU = 4;
} 
else {
	ENIQS_CPU = -1;

}