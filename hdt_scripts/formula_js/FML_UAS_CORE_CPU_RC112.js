//UAS CORE CPU Calculation

var A_CPU

A_CPU =0.0441*CN_NODE+6.668


if (FEATURE_PM == 1){
	A_CPU = A_CPU +0;
}
else if (FEATURE_PM == 0){
	A_CPU = A_CPU;
}


if (FEATURE_EBS == 1){
	A_CPU = A_CPU +0;
}
else if (FEATURE_EBS == 0){
	A_CPU = A_CPU;
}

if (FEATURE_UAS_USERS == 1){
	A_CPU = A_CPU +0;
}
else if (FEATURE_UAS_USERS == 0){
	A_CPU = A_CPU;
}

if (A_CPU>1&&A_CPU<11) {
	UAS_CPU = 1;
} 
else if (A_CPU>11&&A_CPU<17) {
	UAS_CPU = 2;
} 
else if (A_CPU>17&&A_CPU<24) {
	UAS_CPU = 3;
} 
else if (A_CPU>24&&A_CPU<40) {
	UAS_CPU = 4;
} 
else {
	UAS_CPU = -1;
}
