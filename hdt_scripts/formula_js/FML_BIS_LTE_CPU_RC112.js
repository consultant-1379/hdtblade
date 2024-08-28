//BIS LTE CPU Calculation
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

if (FEATURE_BIS_USERS == 1){
	A_CPU = A_CPU +0;
}
else if (FEATURE_BIS_USERS == 0){
	A_CPU = A_CPU;
}

if (A_CPU>1&&A_CPU<9) {
	BIS_CPU = 1;
} 
else if (A_CPU>9&&A_CPU<12) {
	BIS_CPU = 1;
} 
else if (A_CPU>12&&A_CPU<56) {
	BIS_CPU = 1;
} 
else if (A_CPU>56&&A_CPU<96) {
	BIS_CPU = 2;
} 
else {
	BIS_CPU = -1;

}