//UAS LTE & CORE MEM Calculation

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

if (FEATURE_UAS_USERS == 1){
	A_MEM = A_MEM +0;
}
else if (FEATURE_UAS_USERS == 0){
	A_MEM = A_MEM;
}

if (A_MEM > 1 && A_MEM <= 8) {
	UAS_MEM = 8;
} 
else if (A_MEM > 8 && A_MEM <= 16) {
	UAS_MEM = 16;
} 
else if (A_MEM > 16 && A_MEM <= 32) {
	UAS_MEM = 32;
} 
else if (A_MEM > 32 && A_MEM <= 64) {
	UAS_MEM = 64;
} 
else if (A_MEM > 64 && A_MEM <= 96) {
	UAS_MEM = 96;
} 
else if (A_MEM > 96 && A_MEM <= 128) {
	UAS_MEM = 128;
} 
else if (A_MEM > 128 && A_MEM <= 160) {
	UAS_MEM = 160;
} 
else if (A_MEM > 160 && A_MEM <= 192) {
	UAS_MEM = 192;
} 
else if (A_MEM > 192 && A_MEM <= 224) {
	UAS_MEM = 224;
} 
else if (A_MEM > 224 && A_MEM <= 256) {
	UAS_MEM = 256;
} else {
	UAS_MEM = -1;
}