//SWITCH WRAN & CORE IO Calculation

var A_SWITCH

var A_TOTAL = (WR_CELL + CN_NODE)/1.5

A_SWITCH =0.0043*A_TOTAL+34.555

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


if (A_SWITCH <= 60) {
	SWITCH_IO = 15;
} 
else if (A_SWITCH > 60) {
	SWITCH_IO = 45;
} 
else {
	SWITCH_IO = -1;
}



