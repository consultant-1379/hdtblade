//DAE GRAN IO Calculation

var A_DAE = -1.0;


if (SELECTED_PRODUCT == 'RC112_ES112') {
	
	// Determine HW result

	A_DAE =0.0035*GR_CELL + 15.634

if (FEATURE_PM == 1){
	A_DAE = A_DAE + 0;
}
else if (FEATURE_PM == 0){
	
	A_DAE = A_DAE;
}

if (FEATURE_EBS == 1){
	
	A_DAE = A_DAE + 0;
}
else if (FEATURE_EBS == 0){
	
	A_DAE = A_DAE + 0;
}

if (A_DAE > 1 && A_DAE <= 30) {
	DAE_IO = 1;
} 
else if (A_DAE > 30 && A_DAE <= 55) {
	DAE_IO = 2;
} 
else if (A_DAE > 55 && A_DAE <= 110) {
	DAE_IO = 3;
}
else if (A_DAE > 110 && A_DAE <= 140) {
	DAE_IO = 4;
}
else if (A_DAE > 140 && A_DAE <= 150) {
	DAE_IO = 5;
}
else if (A_DAE > 150 && A_DAE <= 180) {
	DAE_IO = 6;
}
else if (A_DAE > 180 && A_DAE <= 210) {
	DAE_IO = 7;
}
else if (A_DAE > 210 && A_DAE <= 240) {
	DAE_IO = 8;
}
else if (A_DAE > 240 && A_DAE <= 270) {
	DAE_IO = 9;
}	
else {
	DAE_IO = -1;
}
	
	
	// return DAE_IO to engine to look up HW for OSS RC 11.2 + ENIQS 11.2
} else if (SELECTED_PRODUCT == 'ES112') {

	// Determine different HW result

	A_DAE =0.0035*GR_CELL+15.634

if (FEATURE_PM == 1){
	A_DAE = A_DAE + 0;
}
else if (FEATURE_PM == 0){
	
	A_DAE = A_DAE;
}

if (FEATURE_EBS == 1){
	
	A_DAE = A_DAE + 0;
}
else if (FEATURE_EBS == 0){
	
	A_DAE = A_DAE;
}

if (A_DAE > 1 && A_DAE <= 35) {
	DAE_IO = 10;
} 
else if (A_DAE > 35 && A_DAE <= 60) {
	DAE_IO = 11;
} 
else if (A_DAE > 60 && A_DAE <= 122) {
	DAE_IO = 12;
}
else if (A_DAE > 122 && A_DAE <= 130) {
	DAE_IO = 13;
}
else if (A_DAE > 130 && A_DAE <= 150) {
	DAE_IO = 14;
}
else if (A_DAE > 150 && A_DAE <= 180) {
	DAE_IO = 15;
}
else if (A_DAE > 180 && A_DAE <= 210) {
	DAE_IO = 16;
}
else if (A_DAE > 210 && A_DAE <= 240) {
	DAE_IO = 17;
}
else if (A_DAE > 240 && A_DAE <= 270) {
	DAE_IO = 18;
}	
else {
	DAE_IO = -1;
}


	// return DAE_IO
} else if (SELECTED_PRODUCT == 'RC112') {

// Determine different HW result

	A_DAE =0.0035*GR_CELL+15.634

if (FEATURE_PM == 1){
	A_DAE = A_DAE + 0;
}
else if (FEATURE_PM == 0){
	
	A_DAE = A_DAE;
}

if (FEATURE_EBS == 1){
	
	A_DAE = A_DAE + 0;
}
else if (FEATURE_EBS == 0){
	
	A_DAE = A_DAE;
}

if (A_DAE > 1 && A_DAE <= 30) {
	DAE_IO = 19;
} 
else if (A_DAE > 30 && A_DAE <= 60) {
	DAE_IO = 20;
} 
else if (A_DAE > 60 && A_DAE <= 90) {
	DAE_IO = 21;
}
else if (A_DAE > 90 && A_DAE <= 120) {
	DAE_IO = 22;
}
else if (A_DAE > 120 && A_DAE <= 150) {
	DAE_IO = 23;
}
else if (A_DAE > 150 && A_DAE <= 180) {
	DAE_IO = 24;
}
else if (A_DAE > 180 && A_DAE <= 210) {
	DAE_IO = 25;
}
else if (A_DAE > 210 && A_DAE <= 240) {
	DAE_IO = 26;
}
else if (A_DAE > 240 && A_DAE <= 270) {
	DAE_IO = 27;
}	
else {
	DAE_IO = -1;
}









}
else if (SELECTED_PRODUCT == 'RC113_ES113') {
	
	// Determine HW result

	A_DAE =0.0035*GR_CELL + 15.634

if (FEATURE_PM == 1){
	A_DAE = A_DAE + 0;
}
else if (FEATURE_PM == 0){
	
	A_DAE = A_DAE;
}

if (FEATURE_EBS == 1){
	
	A_DAE = A_DAE + 0;
}
else if (FEATURE_EBS == 0){
	
	A_DAE = A_DAE + 0;
}

if (A_DAE > 1 && A_DAE <= 30) {
	DAE_IO = 1;
} 
else if (A_DAE > 30 && A_DAE <= 55) {
	DAE_IO = 2;
} 
else if (A_DAE > 55 && A_DAE <= 110) {
	DAE_IO = 3;
}
else if (A_DAE > 110 && A_DAE <= 140) {
	DAE_IO = 4;
}
else if (A_DAE > 140 && A_DAE <= 150) {
	DAE_IO = 5;
}
else if (A_DAE > 150 && A_DAE <= 180) {
	DAE_IO = 6;
}
else if (A_DAE > 180 && A_DAE <= 210) {
	DAE_IO = 7;
}
else if (A_DAE > 210 && A_DAE <= 240) {
	DAE_IO = 8;
}
else if (A_DAE > 240 && A_DAE <= 270) {
	DAE_IO = 9;
}	
else {
	DAE_IO = -1;
}
	
	
	// return DAE_IO to engine to look up HW for OSS RC 11.3 + ENIQS 11.3
} else if (SELECTED_PRODUCT == 'ES113') {

	// Determine different HW result

	A_DAE =0.0035*GR_CELL+15.634

if (FEATURE_PM == 1){
	A_DAE = A_DAE + 0;
}
else if (FEATURE_PM == 0){
	
	A_DAE = A_DAE;
}

if (FEATURE_EBS == 1){
	
	A_DAE = A_DAE + 0;
}
else if (FEATURE_EBS == 0){
	
	A_DAE = A_DAE;
}

if (A_DAE > 1 && A_DAE <= 35) {
	DAE_IO = 10;
} 
else if (A_DAE > 35 && A_DAE <= 60) {
	DAE_IO = 11;
} 
else if (A_DAE > 60 && A_DAE <= 122) {
	DAE_IO = 12;
}
else if (A_DAE > 122 && A_DAE <= 130) {
	DAE_IO = 13;
}
else if (A_DAE > 130 && A_DAE <= 150) {
	DAE_IO = 14;
}
else if (A_DAE > 150 && A_DAE <= 180) {
	DAE_IO = 15;
}
else if (A_DAE > 180 && A_DAE <= 210) {
	DAE_IO = 16;
}
else if (A_DAE > 210 && A_DAE <= 240) {
	DAE_IO = 17;
}
else if (A_DAE > 240 && A_DAE <= 270) {
	DAE_IO = 18;
}	
else {
	DAE_IO = -1;
}


	// return DAE_IO
} else if (SELECTED_PRODUCT == 'RC113') {

// Determine different HW result

	A_DAE =0.0035*GR_CELL+15.634

if (FEATURE_PM == 1){
	A_DAE = A_DAE + 0;
}
else if (FEATURE_PM == 0){
	
	A_DAE = A_DAE;
}

if (FEATURE_EBS == 1){
	
	A_DAE = A_DAE + 0;
}
else if (FEATURE_EBS == 0){
	
	A_DAE = A_DAE;
}

if (A_DAE > 1 && A_DAE <= 30) {
	DAE_IO = 19;
} 
else if (A_DAE > 30 && A_DAE <= 60) {
	DAE_IO = 20;
} 
else if (A_DAE > 60 && A_DAE <= 90) {
	DAE_IO = 21;
}
else if (A_DAE > 90 && A_DAE <= 120) {
	DAE_IO = 22;
}
else if (A_DAE > 120 && A_DAE <= 150) {
	DAE_IO = 23;
}
else if (A_DAE > 150 && A_DAE <= 180) {
	DAE_IO = 24;
}
else if (A_DAE > 180 && A_DAE <= 210) {
	DAE_IO = 25;
}
else if (A_DAE > 210 && A_DAE <= 240) {
	DAE_IO = 26;
}
else if (A_DAE > 240 && A_DAE <= 270) {
	DAE_IO = 27;
}	
else {
	DAE_IO = -1;
}




}
// return DAE_IO

