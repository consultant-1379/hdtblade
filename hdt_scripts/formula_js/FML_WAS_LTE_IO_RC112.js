//WAS LTE IO Calculation

var WAS_IO

if (FEATURE_PM == 1){
	LR_NODE = LR_NODE + 0;
}
else if (FEATURE_PM == 0){
	LR_NODE = LR_NODE;
}

if (FEATURE_EBS == 1){
	LR_NODE = LR_NODE + 0;
}
else if (FEATURE_EBS == 0){
	LR_NODE = LR_NODE;
}

if (FEATURE_WAS_USERS == 1){
	LR_NODE = LR_NODE + 0;
}
else if (FEATURE_WAS_USERS == 0){
	LR_NODE = LR_NODE;
}

if (LR_NODE > 1001) {
	WAS_IO = 45;
} 
else if (LR_NODE <= 1000) {
	WAS_IO = 15;
} 
else {
	WAS_IO = -1;
}