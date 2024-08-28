//SWITCH WRAN IO Calculation


var A_SWITCH

A_SWITCH =0.0035*WR_CELL+15.634

if (FEATURE_PM == 1){
	
	A_SWITCH = A_SWITCH + 0;
}
else if (FEATURE_PM == 0){
	
	A_SWITCH = A_SWITCH;
}

if (FEATURE_EBS == 1){
	
	A_SWITCH = A_SWITCH + 0;
}
else if (FEATURE_EBS == 0){
	
	A_SWITCH = A_SWITCH;
}


if (A_SWITCH <= 50) {
	SWITCH_IO = 15;
} 
else if (A_SWITCH > 50) {
	SWITCH_IO = 45;
} 
else {
	SWITCH_IO = -1;
}

