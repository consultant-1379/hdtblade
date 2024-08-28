//SWITCH GRAN CPU Calculation

var A_CPU

A_CPU =0.0014*GR_CELL+8.0511


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

if (A_CPU>1&&A_CPU<60) {
	SWITCH_CPU = 1;
} 

else 
{
	SWITCH_CPU = -1;
}