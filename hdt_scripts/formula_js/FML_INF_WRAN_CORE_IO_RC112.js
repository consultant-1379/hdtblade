//INF WRAN & CORE IO Calculation

var INF_IO = -1.0;

if (SELECTED_PRODUCT == 'RC112_ES112') {
	
	// Determine HW result

var A_TOTAL = (WR_CELL + CN_NODE)/1.5

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


if (A_TOTAL > 1001) {
	INF_IO = 45;
} 
else if (A_TOTAL <= 1000) {
	INF_IO = 30;
} 
else {
	INF_IO = -1;
}
	
	
	// return INF_IO to engine to look up HW for OSS RC 11.2 + ENIQS 11.2
} else if (SELECTED_PRODUCT == 'ES112') {

	// Determine different HW result

var A_TOTAL = (WR_CELL + CN_NODE)/1.5

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


if (A_TOTAL > 1001) {
	INF_IO = 45;
} 
else if (A_TOTAL <= 1000) {
	INF_IO = 30;
} 
else {
	INF_IO = -1;
} 


	// return INF_IO
} else if (SELECTED_PRODUCT == 'RC112') {

// Determine different HW result

var A_TOTAL = (WR_CELL + CN_NODE)/1.5

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


if (A_TOTAL > 3401) {
	INF_IO = 45;
} 
else if (A_TOTAL <= 3400) {
	INF_IO = 15;
} 
else {
	INF_IO = -1;
} 








}
else if (SELECTED_PRODUCT == 'RC113_ES113') {
	
	// Determine HW result

var A_TOTAL = (WR_CELL + CN_NODE)/1.5

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


if (A_TOTAL > 1001) {
	INF_IO = 45;
} 
else if (A_TOTAL <= 1000) {
	INF_IO = 30;
} 
else {
	INF_IO = -1;
}
	
	
	// return INF_IO to engine to look up HW for OSS RC 11.3 + ENIQS 11.3
} else if (SELECTED_PRODUCT == 'ES113') {

	// Determine different HW result

var A_TOTAL = (WR_CELL + CN_NODE)/1.5

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


if (A_TOTAL > 1001) {
	INF_IO = 45;
} 
else if (A_TOTAL <= 1000) {
	INF_IO = 30;
} 
else {
	INF_IO = -1;
} 


	// return INF_IO
} else if (SELECTED_PRODUCT == 'RC113') {

// Determine different HW result

var A_TOTAL = (WR_CELL + CN_NODE)/1.5

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


if (A_TOTAL > 3401) {
	INF_IO = 45;
} 
else if (A_TOTAL <= 3400) {
	INF_IO = 15;
} 
else {
	INF_IO = -1;
} 

}
// return INF_IO