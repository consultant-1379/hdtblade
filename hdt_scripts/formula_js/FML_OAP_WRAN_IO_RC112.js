//OAP WRAN IO Calculation

var OAP_IO

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


if (WR_CELL > 5001) {
	OAP_IO = 30;
} 
else if (WR_CELL <= 5000) {
	OAP_IO = 15;
} 
else {
	OAP_IO = -1;
}