//WAS WRAN IO Calculation

var WAS_IO

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

if (FEATURE_WAS_USERS == 1){
	WR_CELL = WR_CELL + 0;
}
else if (FEATURE_WAS_USERS == 0){
	WR_CELL = WR_CELL;
}

if (WR_CELL > 5001) {
	WAS_IO = 45;
} 
else if (WR_CELL <= 5000) {
	WAS_IO = 15;
} 
else {
	WAS_IO = -1;
}