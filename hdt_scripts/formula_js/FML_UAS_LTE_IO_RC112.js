//UAS LTE IO Calculation
var UAS_IO

if (SELECTED_PRODUCT == 'RC112_ES112') {
	
	// Determine HW result

if (FEATURE_PM == 1){
	LR_NODE = LR_NODE + 0;
}
else if (FEATURE_PM == 0){
	LR_NODE = LR_NODE;
}

if (FEATURE_EBS == 1){
	LR_NODE = LR_NODE + 0;
}
else if (FEATURE_EBS == 0){
	LR_NODE = LR_NODE;
}

if (FEATURE_UAS_USERS == 1){
	LR_NODE = LR_NODE +0;
}
else if (FEATURE_UAS_USERS == 0){
	LR_NODE = LR_NODE;
}

if (LR_NODE > 3999) {
	UAS_IO = 99;
} 
else if (LR_NODE > 1001) {
	UAS_IO = 45;
} 
else if (LR_NODE <= 1000) {
	UAS_IO = 30; 
} 
else {
	UAS_IO = -1;
}
	
	
	
} else if (SELECTED_PRODUCT == 'ES112') {

	// Determine different HW result

if (FEATURE_PM == 1){
	LR_NODE = LR_NODE + 0;
}
else if (FEATURE_PM == 0){
	LR_NODE = LR_NODE;
}

if (FEATURE_EBS == 1){
	LR_NODE = LR_NODE + 0;
}
else if (FEATURE_EBS == 0){
	LR_NODE = LR_NODE;
}

if (FEATURE_UAS_USERS == 1){
	LR_NODE = LR_NODE +0;
}
else if (FEATURE_UAS_USERS == 0){
	LR_NODE = LR_NODE;
}

if (LR_NODE > 1001) {
	UAS_IO = 45;
} 
else if (LR_NODE <= 1000) {
	UAS_IO = 30; 
} 
else {
	UAS_IO = -1;
}


	
} else if (SELECTED_PRODUCT == 'RC112') {

// Determine different HW result

if (FEATURE_PM == 1){
	LR_NODE = LR_NODE + 0;
}
else if (FEATURE_PM == 0){
	LR_NODE = LR_NODE;
}

if (FEATURE_EBS == 1){
	LR_NODE = LR_NODE + 0;
}
else if (FEATURE_EBS == 0){
	LR_NODE = LR_NODE;
}

if (FEATURE_UAS_USERS == 1){
	LR_NODE = LR_NODE +0;
}
else if (FEATURE_UAS_USERS == 0){
	LR_NODE = LR_NODE;
}

if (LR_NODE > 1001) {
	UAS_IO = 45;
} 
else if (LR_NODE <= 1000) {
	UAS_IO = 30; 
} 
else {
	UAS_IO = -1;
}


}
