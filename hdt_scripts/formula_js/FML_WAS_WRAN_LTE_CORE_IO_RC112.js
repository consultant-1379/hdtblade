//WAS WRAN & LTE & CORE IO Calculation

var WAS_IO

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

if (FEATURE_WAS_USERS == 1){
	A_TOTAL = A_TOTAL +0;
}
else if (FEATURE_WAS_USERS == 0){
	A_TOTAL = A_TOTAL;
}

if (A_TOTAL > 2801) {
	WAS_IO = 45;
} 
else if (A_TOTAL <= 2800) {
	WAS_IO = 15;
} 
else {
	WAS_IO = -1;
}