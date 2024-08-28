//INF LTE IO Calculation

var INF_IO = -1.0;

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

if (LR_NODE > 1001) {
	INF_IO = 45;
} 
else if (LR_NODE <= 1000) {
	INF_IO = 30;
} 
else {
	INF_IO = -1;
} 
	
	
	// return INF_IO to engine to look up HW for OSS RC 11.2 + ENIQS 11.2
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

if (LR_NODE > 1001) {
	INF_IO = 45;
} 
else if (LR_NODE <= 1000) {
	INF_IO = 30;
} 
else {
	INF_IO = -1;
} 


	// return INF_IO
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

if (LR_NODE > 1001) {
	INF_IO = 45;
} 
else if (LR_NODE <= 1000) {
	INF_IO = 15;
} 
else {
	INF_IO = -1;
} 









}
else if (SELECTED_PRODUCT == 'RC113_ES113') {
	
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

if (LR_NODE > 1001) {
	INF_IO = 45;
} 
else if (LR_NODE <= 1000) {
	INF_IO = 30;
} 
else {
	INF_IO = -1;
} 
	
	
	// return INF_IO to engine to look up HW for OSS RC 11.3 + ENIQS 11.3
} else if (SELECTED_PRODUCT == 'ES113') {

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

if (LR_NODE > 1001) {
	INF_IO = 45;
} 
else if (LR_NODE <= 1000) {
	INF_IO = 30;
} 
else {
	INF_IO = -1;
} 


	// return INF_IO
} else if (SELECTED_PRODUCT == 'RC113') {

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

if (LR_NODE > 1001) {
	INF_IO = 45;
} 
else if (LR_NODE <= 1000) {
	INF_IO = 15;
} 
else {
	INF_IO = -1;
} 


}
// return INF_IO