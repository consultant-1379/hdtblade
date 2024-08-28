//NEDSS LTE & CORE IO Calculation

var NEDSS_IO

var A_TOTAL = (LR_NODE + CN_NODE)/1.5

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


if (A_TOTAL > 101) {
	NEDSS_IO = 45;
} 
else if (A_TOTAL <= 100) {
	NEDSS_IO = 30;
} 
else {
	NEDSS_IO = -1;
}