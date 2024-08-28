//OAP WRAN & LTE IO Calculation

var OAP_IO

var A_TOTAL = (WR_CELL + LR_NODE)/1.5

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

if (A_TOTAL > 3671) {
	OAP_IO = 30;
} 
else if (A_TOTAL <= 3670) {
	OAP_IO = 15;
} 
else {
	OAP_IO = -1;
}