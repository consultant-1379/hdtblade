//STORAGE WRAN & CORE CPU Calculation

var A_CPU

var A_TOTAL = WR_CELL*1.2 + CN_NODE*3
//A_CPU =7.4435*Math.exp(0.0005*A_TOTAL)

A_CPU=(0.0165*A_TOTAL+3.5822)/8


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

if (A_CPU>1&&A_CPU<160) {
	STORAGE_CPU = 1;
} 

else 
{
	STORAGE_CPU = -1;
}