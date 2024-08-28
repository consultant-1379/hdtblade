//OMSAS WRAN & CORE MEM Calculation

var A_MEM

var A_TOTAL = WR_CELL*1.2 + CN_NODE*3
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
	OMSAS_MEM = 8;
} 
else if (A_MEM > 8 && A_MEM <= 16) {
	OMSAS_MEM = 16;
} 
else if (A_MEM > 16 && A_MEM <= 32) {
	OMSAS_MEM = 32;
} 
else if (A_MEM > 32 && A_MEM <= 64) {
	OMSAS_MEM = 64;
} 
else if (A_MEM > 64 && A_MEM <= 256) {
	OMSAS_MEM = 96;

} else {
	OMSAS_MEM = -1;
}