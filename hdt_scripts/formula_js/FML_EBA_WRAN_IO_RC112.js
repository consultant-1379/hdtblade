//EBA WRAN IO Calculation

var EBA_IO

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
	EBA_IO = 45;
} 
else if (WR_CELL <= 5000) {
	EBA_IO = 15;
} 
else {
	EBA_IO = -1;
}