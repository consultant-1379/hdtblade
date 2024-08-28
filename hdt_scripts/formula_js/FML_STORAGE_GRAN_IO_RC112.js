//STORAGE GRAN IO Calculation

var A_STORAGE = -1.0;

if (SELECTED_PRODUCT == 'RC112_ES112') {
	
	// Determine HW result

A_STORAGE =0.0035*GR_CELL+15.634

if (FEATURE_PM == 1){
	
	A_STORAGE = A_STORAGE + 0;
}
else if (FEATURE_PM == 0){
	
	A_STORAGE = A_STORAGE;
}

if (FEATURE_EBS == 1){
	
	A_STORAGE = A_STORAGE + 0;
}
else if (FEATURE_EBS == 0){
	
	A_STORAGE = A_STORAGE;
}


if (A_STORAGE <= 90) {
	STORAGE_IO = 30;
} 
else if (A_STORAGE > 90) {
	STORAGE_IO = 45;
} 
else {
	STORAGE_IO = -1;
}
	
	
	// return STORAGE_IO to engine to look up HW for OSS RC 11.2 + ENIQS 11.2
} else if (SELECTED_PRODUCT == 'ES112') {

	// Determine different HW result

A_STORAGE =0.0035*GR_CELL+15.634

if (FEATURE_PM == 1){
	
	A_STORAGE = A_STORAGE + 0;
}
else if (FEATURE_PM == 0){
	
	A_STORAGE = A_STORAGE;
}

if (FEATURE_EBS == 1){
	
	A_STORAGE = A_STORAGE + 0;
}
else if (FEATURE_EBS == 0){
	
	A_STORAGE = A_STORAGE;
}


if (A_STORAGE <= 130) {
	STORAGE_IO = 30;
} 
else if (A_STORAGE > 130) {
	STORAGE_IO = 45;
} 
else {
	STORAGE_IO = -1;
}


	// return STORAGE_IO
} else if (SELECTED_PRODUCT == 'RC112') {

// Determine different HW result

A_STORAGE =0.0035*GR_CELL+15.634

if (FEATURE_PM == 1){
	
	A_STORAGE = A_STORAGE + 0;
}
else if (FEATURE_PM == 0){
	
	A_STORAGE = A_STORAGE;
}

if (FEATURE_EBS == 1){
	
	A_STORAGE = A_STORAGE + 0;
}
else if (FEATURE_EBS == 0){
	
	A_STORAGE = A_STORAGE;
}


if (A_STORAGE <= 130) {
	STORAGE_IO = 30;
} 
else if (A_STORAGE > 130) {
	STORAGE_IO = 45;
} 
else {
	STORAGE_IO = -1;
}










}
else if  (SELECTED_PRODUCT == 'RC113_ES113') {
	
	// Determine HW result

A_STORAGE =0.0035*GR_CELL+15.634

if (FEATURE_PM == 1){
	
	A_STORAGE = A_STORAGE + 0;
}
else if (FEATURE_PM == 0){
	
	A_STORAGE = A_STORAGE;
}

if (FEATURE_EBS == 1){
	
	A_STORAGE = A_STORAGE + 0;
}
else if (FEATURE_EBS == 0){
	
	A_STORAGE = A_STORAGE;
}


if (A_STORAGE <= 90) {
	STORAGE_IO = 30;
} 
else if (A_STORAGE > 90) {
	STORAGE_IO = 45;
} 
else {
	STORAGE_IO = -1;
}
	
	
	// return STORAGE_IO to engine to look up HW for OSS RC 11.3 + ENIQS 11.3
} else if (SELECTED_PRODUCT == 'ES113') {

	// Determine different HW result

A_STORAGE =0.0035*GR_CELL+15.634

if (FEATURE_PM == 1){
	
	A_STORAGE = A_STORAGE + 0;
}
else if (FEATURE_PM == 0){
	
	A_STORAGE = A_STORAGE;
}

if (FEATURE_EBS == 1){
	
	A_STORAGE = A_STORAGE + 0;
}
else if (FEATURE_EBS == 0){
	
	A_STORAGE = A_STORAGE;
}


if (A_STORAGE <= 130) {
	STORAGE_IO = 30;
} 
else if (A_STORAGE > 130) {
	STORAGE_IO = 45;
} 
else {
	STORAGE_IO = -1;
}


	// return STORAGE_IO
} else if (SELECTED_PRODUCT == 'RC113') {

// Determine different HW result

A_STORAGE =0.0035*GR_CELL+15.634

if (FEATURE_PM == 1){
	
	A_STORAGE = A_STORAGE + 0;
}
else if (FEATURE_PM == 0){
	
	A_STORAGE = A_STORAGE;
}

if (FEATURE_EBS == 1){
	
	A_STORAGE = A_STORAGE + 0;
}
else if (FEATURE_EBS == 0){
	
	A_STORAGE = A_STORAGE;
}


if (A_STORAGE <= 130) {
	STORAGE_IO = 30;
} 
else if (A_STORAGE > 130) {
	STORAGE_IO = 45;
} 
else {
	STORAGE_IO = -1;
}


}
// return STORAGE_IO