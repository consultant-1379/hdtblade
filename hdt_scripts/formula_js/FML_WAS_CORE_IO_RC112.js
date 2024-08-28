//WAS CORE IO Calculation

var WAS_IO

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

if (FEATURE_WAS_USERS == 1){
	CN_NODE = CN_NODE + 0;
}
else if (FEATURE_WAS_USERS == 0){
	CN_NODE = CN_NODE;
}

if (CN_NODE > 101) {
	WAS_IO = 45;
} 
else if (CN_NODE <= 100) {
	WAS_IO = 15;
} 
else {
	WAS_IO = -1;
}