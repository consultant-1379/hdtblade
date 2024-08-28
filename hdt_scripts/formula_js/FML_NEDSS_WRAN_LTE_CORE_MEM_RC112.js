//NEDSS WRAN & LTE & CORE MEM Calculation

var A_MEM

var A_TOTAL = LR_NODE*1 + CN_NODE*3 + WR_CELL*1.2 
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

if (A_MEM > 1 && A_MEM <= 8) {
	NEDSS_MEM = 8;
} 
else if (A_MEM > 8 && A_MEM <= 256) {
	NEDSS_MEM = 16;

} else {
	NEDSS_MEM = -1;
}