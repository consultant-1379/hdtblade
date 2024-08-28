//OMSAS WRAN & LTE & CORE IO Calculation

var OMSAS_IO

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
	OMSAS_IO = 45;
} 
else if (A_TOTAL <= 100) {
	OMSAS_IO = 30;
} 
else {
	OMSAS_IO = -1;
}
