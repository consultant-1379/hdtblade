//ADMIN CORE IO Calculation

var ADMIN_IO = -1.0;

if (SELECTED_PRODUCT == 'RC112_ES112') 
{
	
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

if (FEATURE_STORAGE_CHECKPOINTS == 1){
	CN_NODE = CN_NODE + 0;
}
else if (FEATURE_STORAGE_CHECKPOINTS == 0){
	CN_NODE = CN_NODE;
}

if (CN_NODE > 101) {
	ADMIN_IO = 45;
} 
else if (CN_NODE <=100) {
	ADMIN_IO = 30;
}
else {
	ADMIN_IO = -1;
} 
	
	
	// return ADMIN_IO to engine to look up HW for OSS RC 11.2 + ENIQS 11.2
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

if (FEATURE_STORAGE_CHECKPOINTS == 1){
	CN_NODE = CN_NODE + 0;
}
else if (FEATURE_STORAGE_CHECKPOINTS == 0){
	CN_NODE = CN_NODE;
}

if (CN_NODE > 101) {
	ADMIN_IO = 45;
} 
else if (CN_NODE <=100) {
	ADMIN_IO = 30;
}
else {
	ADMIN_IO = -1;
} 


	// return ADMIN_IO
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

if (FEATURE_STORAGE_CHECKPOINTS == 1){
	CN_NODE = CN_NODE + 0;
}
else if (FEATURE_STORAGE_CHECKPOINTS == 0){
	CN_NODE = CN_NODE;
}

if (CN_NODE > 101) {
	ADMIN_IO = 45;
} 
else if (CN_NODE <=100) {
	ADMIN_IO = 15;
}
else {
	ADMIN_IO = -1;
} 





}
else if (SELECTED_PRODUCT == 'RC113_ES113') {
	
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

if (FEATURE_STORAGE_CHECKPOINTS == 1){
	CN_NODE = CN_NODE + 0;
}
else if (FEATURE_STORAGE_CHECKPOINTS == 0){
	CN_NODE = CN_NODE;
}

if (CN_NODE > 101) {
	ADMIN_IO = 45;
} 
else if (CN_NODE <=100) {
	ADMIN_IO = 30;
}
else {
	ADMIN_IO = -1;
} 
	
	
	// return ADMIN_IO to engine to look up HW for OSS RC 11.3 + ENIQS 11.3
} else if (SELECTED_PRODUCT == 'ES113') {

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

if (FEATURE_STORAGE_CHECKPOINTS == 1){
	CN_NODE = CN_NODE + 0;
}
else if (FEATURE_STORAGE_CHECKPOINTS == 0){
	CN_NODE = CN_NODE;
}

if (CN_NODE > 101) {
	ADMIN_IO = 45;
} 
else if (CN_NODE <=100) {
	ADMIN_IO = 30;
}
else {
	ADMIN_IO = -1;
} 


	// return ADMIN_IO
} else if (SELECTED_PRODUCT == 'RC113') {

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

if (FEATURE_STORAGE_CHECKPOINTS == 1){
	CN_NODE = CN_NODE + 0;
}
else if (FEATURE_STORAGE_CHECKPOINTS == 0){
	CN_NODE = CN_NODE;
}

if (CN_NODE > 101) {
	ADMIN_IO = 45;
} 
else if (CN_NODE <=100) {
	ADMIN_IO = 15;
}
else {
	ADMIN_IO = -1;
} 


}

// return ADMIN_IO