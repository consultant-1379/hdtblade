//UAS LTE CPU Calculation

var A_CPU

A_CPU =7.1592*Math.exp(0.0005*LR_NODE)

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

if (A_CPU>1&&A_CPU<9) {
	UAS_CPU = 1;
} 
else if (A_CPU>9&&A_CPU<12) {
	UAS_CPU = 2;
} 
else if (A_CPU>12&&A_CPU<56) {
	UAS_CPU = 3;
} 
else if (A_CPU>56&&A_CPU<96) {
	UAS_CPU = 4;
} 
else {
	UAS_CPU = -1;
}
