//BIS WRAN CPU Calculation

var A_CPU

A_CPU =0.0014*WR_CELL+8.0511


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

if (A_CPU>1&&A_CPU<10) {
	BIS_CPU = 1;
} 
else if (A_CPU>10&&A_CPU<15) {
	BIS_CPU = 1;
} 
else if (A_CPU>15&&A_CPU<21) {
	BIS_CPU = 1;
} 
else if (A_CPU>21&&A_CPU<60) {
	BIS_CPU = 2;
}
else 
{
	BIS_CPU = -1;
}
