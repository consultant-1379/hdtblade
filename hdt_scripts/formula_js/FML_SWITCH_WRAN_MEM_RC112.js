//SWITCH WRAN MEM Calculation

var A_MEM

A_MEM =0.0035*WR_CELL+15.634

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
	SWITCH_MEM = 8;

} else {
	SWITCH_MEM = -1;
}