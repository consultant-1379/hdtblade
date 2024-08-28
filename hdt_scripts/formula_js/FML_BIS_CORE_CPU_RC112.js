//BIS CORE CPU Calculation

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

if (FEATURE_BIS_USERS == 1){
	A_CPU = A_CPU +10;
}
else if (FEATURE_BIS_USERS == 0){
	A_CPU = A_CPU;
}

if (A_CPU>1&&A_CPU<11) {
	BIS_CPU = 1;
} 
else if (A_CPU>11&&A_CPU<17) {
	BIS_CPU = 1;
} 
else if (A_CPU>17&&A_CPU<24) {
	BIS_CPU = 1;
} 
else if (A_CPU>24&&A_CPU<40) {
	BIS_CPU = 2;
} 
else {
	BIS_CPU = -1;
}