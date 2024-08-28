//OAP CORE IO Calculation

var OAP_IO

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
	OAP_IO = 30;
} 
else if (CN_NODE <= 100) {
	OAP_IO = 15;
} 
else {
	OAP_IO = -1;
}