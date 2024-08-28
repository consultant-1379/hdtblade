//BIS GRAN IO Calculation

var BIS_IO

if (FEATURE_PM == 1){
	GR_CELL = GR_CELL + 0;
}
else if (FEATURE_PM == 0){
	GR_CELL = GR_CELL;
}


if (FEATURE_EBS == 1){
	GR_CELL = GR_CELL + 0;
}
else if (FEATURE_EBS == 0){
	GR_CELL = GR_CELL;
}

if (FEATURE_BIS_USERS == 1){
	GR_CELL = GR_CELL + 0;
}
else if (FEATURE_BIS_USERS == 0){
	GR_CELL = GR_CELL;
}

if (GR_CELL > 1001) {
	BIS_IO = 45;
} 
else if (GR_CELL <= 1000) {
	BIS_IO = 30;
} 
else {
	BIS_IO = -1;
}