//UAS WRAN & LTE IO Calculation
var UAS_IO

if (SELECTED_PRODUCT == 'RC112_ES112') {
	
	// Determine HW result

var A_TOTAL =(WR_CELL + LR_NODE)/1.5

if (FEATURE_PM == 1){
	A_TOTAL = A_TOTAL + 0;
}
else if (FEATURE_PM == 0){
	A_TOTAL = A_TOTAL;
}

if (FEATURE_EBS == 1){
	A_TOTAL = A_TOTAL + 0;
}
else if (FEATURE_EBS == 0){
	A_TOTAL = A_TOTAL;
}

if (FEATURE_UAS_USERS == 1){
	A_TOTAL = A_TOTAL +0;
}
else if (FEATURE_UAS_USERS == 0){
	A_TOTAL = A_TOTAL;
}

if (A_TOTAL > 13600) {
	UAS_IO = 99;
} 
else if (A_TOTAL > 1001) {
	UAS_IO = 45;
} 
else if (A_TOTAL <= 1000) {
	UAS_IO = 30; 
} 
else {
	UAS_IO = -1;
}
	
	
	
} else if (SELECTED_PRODUCT == 'ES112') {

	// Determine different HW result

var A_TOTAL =(WR_CELL + LR_NODE)/1.5

if (FEATURE_PM == 1){
	A_TOTAL = A_TOTAL + 0;
}
else if (FEATURE_PM == 0){
	A_TOTAL = A_TOTAL;
}

if (FEATURE_EBS == 1){
	A_TOTAL = A_TOTAL + 0;
}
else if (FEATURE_EBS == 0){
	A_TOTAL = A_TOTAL;
}

if (FEATURE_UAS_USERS == 1){
	A_TOTAL = A_TOTAL +0;
}
else if (FEATURE_UAS_USERS == 0){
	A_TOTAL = A_TOTAL;
}

if (A_TOTAL > 1001) {
	UAS_IO = 45;
} 
else if (A_TOTAL <= 1000) {
	UAS_IO = 30; 
} 
else {
	UAS_IO = -1;
}


	
} else if (SELECTED_PRODUCT == 'RC112') {

// Determine different HW result

var A_TOTAL =(WR_CELL + LR_NODE)/1.5

if (FEATURE_PM == 1){
	A_TOTAL = A_TOTAL + 0;
}
else if (FEATURE_PM == 0){
	A_TOTAL = A_TOTAL;
}

if (FEATURE_EBS == 1){
	A_TOTAL = A_TOTAL + 0;
}
else if (FEATURE_EBS == 0){
	A_TOTAL = A_TOTAL;
}

if (FEATURE_UAS_USERS == 1){
	A_TOTAL = A_TOTAL +0;
}
else if (FEATURE_UAS_USERS == 0){
	A_TOTAL = A_TOTAL;
}

if (A_TOTAL > 1001) {
	UAS_IO = 45;
} 
else if (A_TOTAL <= 1000) {
	UAS_IO = 30; 
} 
else {
	UAS_IO = -1;
}


}