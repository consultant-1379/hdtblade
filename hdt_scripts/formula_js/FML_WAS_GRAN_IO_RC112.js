//WAS GRAN IO Calculation

var WAS_IO

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

if (FEATURE_WAS_USERS == 1){
	GR_CELL = GR_CELL + 0;
}
else if (FEATURE_WAS_USERS == 0){
	GR_CELL = GR_CELL;
}

if (GR_CELL > 1001) {
	WAS_IO = 45;
} 
else if (GR_CELL <= 1000) {
	WAS_IO = 15;
}
else {
	WAS_IO = -1;
}