//ADMIN LTE MEM Calculation

var A_MEM


A_MEM =0.0165*LR_NODE +22.386


if (FEATURE_PM == 1){
	A_MEM = A_MEM +1;
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
	ADMIN_MEM = 16;
} 
else if (A_MEM > 8 && A_MEM <= 16) {
	ADMIN_MEM = 32;
} 
else if (A_MEM > 16 && A_MEM <= 32) {
	ADMIN_MEM = 32+32;
} 
else if (A_MEM > 32 && A_MEM <= 64) {
	ADMIN_MEM = 64+32;
} 
else if (A_MEM > 64 && A_MEM <= 96) {
	ADMIN_MEM = 96+32;
} 
else if (A_MEM > 96 && A_MEM <= 128) {
	ADMIN_MEM = 128;
} 
else if (A_MEM > 128 && A_MEM <= 160) {
	ADMIN_MEM = 160;
} 
else if (A_MEM > 160 && A_MEM <= 192) {
	ADMIN_MEM = 192;
} 
else if (A_MEM > 192 && A_MEM <= 224) {
	ADMIN_MEM = 224;
} 
else if (A_MEM > 224 && A_MEM <= 256) {
	ADMIN_MEM = 256;
} else {
	ADMIN_MEM = -1;
}