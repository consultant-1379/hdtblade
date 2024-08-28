//BIS LTE IO Calculation
var BIS_IO

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

if (FEATURE_BIS_USERS == 1){
	LR_NODE = LR_NODE + 0;
}
else if (FEATURE_BIS_USERS == 0){
	LR_NODE = LR_NODE;
}

if (LR_NODE > 1001) {
	BIS_IO = 45;
} 
else if (LR_NODE <= 1000) {
	BIS_IO = 30;
} 
else {
	BIS_IO = -1;
}

