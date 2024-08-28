//NEDSS WRAN IO Calculation

var NEDSS_IO

if (FEATURE_PM == 1){
	WR_CELL = WR_CELL + 0;
}
else if (FEATURE_PM == 0){
	WR_CELL = WR_CELL;
}


if (FEATURE_EBS == 1){
	WR_CELL = WR_CELL + 0;
}
else if (FEATURE_EBS == 0){
	WR_CELL = WR_CELL;
}


if (WR_CELL > 1001) {
	NEDSS_IO = 45;
} 
else if (WR_CELL <<= 1000) {
	NEDSS_IO = 30;
} 
else {
	NEDSS_IO = -1;
}