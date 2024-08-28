//ENIQS LTE IO Calculation

var ENIQS_IO
//var ENIQS_Counter_Millions


if (PM_COUNT >= 40) {
	ENIQS_IO = 45;
} 
else if (PM_COUNT < 40) {
	ENIQS_IO = 30;
} 
else {
	ENIQS_IO = -1;
}