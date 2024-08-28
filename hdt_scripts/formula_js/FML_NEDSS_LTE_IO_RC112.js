//NEDSS LTE IO Calculation

var NEDSS_IO

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



if (LR_NODE > 1001) {
	NEDSS_IO = 45;
} 
else if (LR_NODE <= 1000) {
	NEDSS_IO = 30;
} 
else {
	NEDSS_IO = -1;
}