//MWS WRAN IO Calculation

var MWS_IO

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
	MWS_IO = 30;
} 
else if (WR_CELL <= 1000) {
	MWS_IO = 30;
} 
else {
	MWS_IO = -1;
}