//MWS LTE & CORE CPU Calculation

var A_CPU

var A_TOTAL = LR_NODE*1 + CN_NODE*3
//A_CPU =7.4435*Math.exp(0.0005*A_TOTAL)

A_CPU =0.0165*A_TOTAL+3.5822


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

if (A_CPU>1&&A_CPU<11) {
	MWS_CPU = 1;
} 
else if (A_CPU>11&&A_CPU<17) {
	MWS_CPU = 1;
} 
else if (A_CPU>17&&A_CPU<24) {
	MWS_CPU = 1;
} 
else if (A_CPU>24&&A_CPU<140) {
	MWS_CPU = 1;
} 
else {
	MWS_CPU = -1;
}