//OMSAS CORE IO Calculation

var OMSAS_IO

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
	OMSAS_IO = 45;
} 
else if (CN_NODE <= 100) {
	OMSAS_IO = 30;
} 
else {
	OMSAS_IO = -1;
}
