//UAS CORE IO Calculation

var UAS_IO

if (SELECTED_PRODUCT == 'RC112_ES112') {
	
	// Determine HW result

	if (FEATURE_PM == 1){
	CN_NODE = CN_NODE + 0;
}
else if (FEATURE_PM == 0){
	CN_NODE = CN_NODE;
}

if (FEATURE_EBS == 1){
	CN_NODE = CN_NODE + 0;
}
else if (FEATURE_EBS == 0){
	CN_NODE = CN_NODE;
}

if (FEATURE_UAS_USERS == 1){
	CN_NODE = CN_NODE +0;
}
else if (FEATURE_UAS_USERS == 0){
	CN_NODE = CN_NODE;
}

if (CN_NODE > 549) {
	UAS_IO = 99;
}
else if (CN_NODE > 101) {
	UAS_IO = 45;
} 
else if (CN_NODE <= 100) {
	UAS_IO = 30;
} 
else {
	UAS_IO = -1;
}
	
	
	
} else if (SELECTED_PRODUCT == 'ES112') {

	// Determine different HW result

if (FEATURE_PM == 1){
	CN_NODE = CN_NODE + 0;
}
else if (FEATURE_PM == 0){
	CN_NODE = CN_NODE;
}

if (FEATURE_EBS == 1){
	CN_NODE = CN_NODE + 0;
}
else if (FEATURE_EBS == 0){
	CN_NODE = CN_NODE;
}

if (FEATURE_UAS_USERS == 1){
	CN_NODE = CN_NODE +0;
}
else if (FEATURE_UAS_USERS == 0){
	CN_NODE = CN_NODE;
}

if (CN_NODE > 101) {
	UAS_IO = 45;
} 
else if (CN_NODE <= 100) {
	UAS_IO = 30;
} 
else {
	UAS_IO = -1;
}


	
} else if (SELECTED_PRODUCT == 'RC112') {

// Determine different HW result

	if (FEATURE_PM == 1){
	CN_NODE = CN_NODE + 0;
}
else if (FEATURE_PM == 0){
	CN_NODE = CN_NODE;
}

if (FEATURE_EBS == 1){
	CN_NODE = CN_NODE + 0;
}
else if (FEATURE_EBS == 0){
	CN_NODE = CN_NODE;
}

if (FEATURE_UAS_USERS == 1){
	CN_NODE = CN_NODE +0;
}
else if (FEATURE_UAS_USERS == 0){
	CN_NODE = CN_NODE;
}

if (CN_NODE > 101) {
	UAS_IO = 45;
} 
else if (CN_NODE <= 100) {
	UAS_IO = 30;
} 
else {
	UAS_IO = -1;
}


}
