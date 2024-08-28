//STORAGE CORE MEM Calculation

var A_MEM

A_MEM =0.0661*CN_NODE+15.536

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
	STORAGE_MEM = 8;

} else {
	STORAGE_MEM = -1;
}