//WAS WRAN MEM Calculation

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

if (FEATURE_WAS_USERS == 1){
	A_MEM = A_MEM +0;
}
else if (FEATURE_WAS_USERS == 0){
	A_MEM = A_MEM;
}

if (A_MEM > 1 && A_MEM <= 8) {
	WAS_MEM = 8;
} 
else if (A_MEM > 8 && A_MEM <= 16) {
	WAS_MEM = 16;
} 
else if (A_MEM > 16 && A_MEM <= 32) {
	WAS_MEM = 32;
} 
else if (A_MEM > 32 && A_MEM <= 64) {
	WAS_MEM = 64;
} 
else if (A_MEM > 64 && A_MEM <= 256) {
	WAS_MEM = 96;

} else {
	WAS_MEM = -1;
}