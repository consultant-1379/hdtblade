//UAS WRAN IO Calculation
var UAS_IO

if (SELECTED_PRODUCT == 'RC112_ES112') {
	
	// Determine HW result

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

if (FEATURE_UAS_USERS == 1){
	WR_CELL = WR_CELL +0;
}
else if (FEATURE_UAS_USERS == 0){
	WR_CELL = WR_CELL;
}

if (WR_CELL > 29999) {
	UAS_IO = 99;
}	
else if (WR_CELL > 1001) {
	UAS_IO = 45;
} 
else if (WR_CELL <= 1000) {
	UAS_IO = 30;
} 
else {
	UAS_IO = -1;
}
	
	
	
} else if (SELECTED_PRODUCT == 'ES112') {

	// Determine different HW result

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

if (FEATURE_UAS_USERS == 1){
	WR_CELL = WR_CELL +0;
}
else if (FEATURE_UAS_USERS == 0){
	WR_CELL = WR_CELL;
}

if (WR_CELL > 1001) {
	UAS_IO = 45;
} 
else if (WR_CELL <= 1000) {
	UAS_IO = 30;
} 
else {
	UAS_IO = -1;
}


	
} else if (SELECTED_PRODUCT == 'RC112') {

// Determine different HW result

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

if (FEATURE_UAS_USERS == 1){
	WR_CELL = WR_CELL +0;
}
else if (FEATURE_UAS_USERS == 0){
	WR_CELL = WR_CELL;
}

if (WR_CELL > 1001) {
	UAS_IO = 45;
} 
else if (WR_CELL <= 1000) {
	UAS_IO = 30;
} 
else {
	UAS_IO = -1;
}


}