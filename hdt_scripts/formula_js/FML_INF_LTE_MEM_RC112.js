//INF LTE MEM Calculation

var A_MEM

A_MEM =0.0165*LR_NODE +22.386

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
	INF_MEM = 8;
} 
else if (A_MEM > 8 && A_MEM <= 16) {
	INF_MEM = 16;
} 
else if (A_MEM > 16 && A_MEM <= 32) {
	INF_MEM = 32;
} 
else if (A_MEM > 32 && A_MEM <= 64) {
	INF_MEM = 64;
} 
else if (A_MEM > 64 && A_MEM <= 256) {
	INF_MEM = 96;
} else {
	INF_MEM = -1;
}