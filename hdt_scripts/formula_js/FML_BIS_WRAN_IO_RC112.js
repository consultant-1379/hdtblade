//BIS WRAN IO Calculation

var BIS_IO

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

if (FEATURE_BIS_USERS == 1){
	WR_CELL = WR_CELL + 0;
}
else if (FEATURE_BIS_USERS == 0){
	WR_CELL = WR_CELL;
}

if (WR_CELL > 1001) {
	BIS_IO = 45;
} 
else if (WR_CELL <= 1000) {
	BIS_IO = 30;
} 
else {
	BIS_IO = -1;
}