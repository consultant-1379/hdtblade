//EBA LTE & CORE MEM Calculation

var A_MEM

var A_TOTAL = LR_NODE*1 + CN_NODE*3
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
	EBA_MEM = 8;
} 
else if (A_MEM > 8 && A_MEM <= 16) {
	EBA_MEM = 16;
} 
else if (A_MEM > 16 && A_MEM <= 32) {
	EBA_MEM = 32;
} 
else if (A_MEM > 32 && A_MEM <= 64) {
	EBA_MEM = 64;
} 
else if (A_MEM > 64 && A_MEM <= 256) {
	EBA_MEM = 96;

} else {
	EBA_MEM = -1;
}