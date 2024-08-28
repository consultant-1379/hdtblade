//MWS CORE IO Calculation

var MWS_IO

if (FEATURE_PM == 1){
	CN_NODE = CN_NODE + 0;
}
else if (FEATURE_PM == 0){
	CN_NODE = CN_NODE;
}

if (FEATURE_EBS == 1){
	CN_NODE = CN_NODE + 0;
}
else if (FEATURE_EBS == 0){
	CN_NODE = CN_NODE;
}


if (CN_NODE > 101) {
	MWS_IO = 30;
} 
else if (CN_NODE <= 100) {
	MWS_IO = 30;
} 
else {
	MWS_IO = -1;
}