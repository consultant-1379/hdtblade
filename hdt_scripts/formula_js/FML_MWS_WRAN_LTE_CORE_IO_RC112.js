//MWS WRAN & LTE & CORE IO Calculation

var MWS_IO

var A_TOTAL = (WR_CELL + LR_NODE + CN_NODE)/2

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
	MWS_IO = 30;
} 
else if (A_TOTAL <= 100) {
	MWS_IO = 30;
} 
else {
	MWS_IO = -1;
}