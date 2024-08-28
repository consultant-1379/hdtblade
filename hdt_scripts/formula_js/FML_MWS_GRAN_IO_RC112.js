//MWS GRAN IO Calculation

var MWS_IO

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


if (GR_CELL > 1001) {
	MWS_IO = 30;
} 
else if (GR_CELL <= 1000) {
	MWS_IO = 30;
} 
else {
	MWS_IO = -1;
}