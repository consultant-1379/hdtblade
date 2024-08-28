//BIS WRAN & LTE & CORE IO Calculation

var BIS_IO

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

if (FEATURE_BIS_USERS == 1){
	A_TOTAL = A_TOTAL +0;
}
else if (FEATURE_BIS_USERS == 0){
	A_TOTAL = A_TOTAL;
}

if (A_TOTAL > 101) {
	BIS_IO = 45;
} 
else if (A_TOTAL <= 100) {
	BIS_IO = 30;
} 
else {
	BIS_IO = -1;
}
