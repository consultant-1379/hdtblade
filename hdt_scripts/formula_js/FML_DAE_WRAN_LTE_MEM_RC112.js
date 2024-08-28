//DAE WRAN & LTE MEM Calculation

var A_MEM

var A_TOTAL = WR_CELL*1.2 + LR_NODE*1
A_MEM =0.0043*A_TOTAL+34.555

if (FEATURE_PM == 1){
	A_MEM = A_MEM +0;
}
else if (FEATURE_PM == 0){
	A_MEM = A_MEM;
}


if (FEATURE_EBS == 1){
	A_MEM = A_MEM +0;
}
else if (FEATURE_EBS == 0){
	A_MEM = A_MEM;
}

if (A_MEM > 1 && A_MEM <= 256) {
	DAE_MEM = 8;

} else {
	DAE_MEM = -1;
}