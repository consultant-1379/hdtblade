//UAS USER Calculation

var UAS_CPU;

if (UAS_USERS >=1 && UAS_USERS < 51 ) {
	UAS_CPU = 1;
} else if (UAS_USERS >= 51 && UAS_USERS < 101) {
	UAS_CPU = 2;
} else if (UAS_USERS >= 101 && UAS_USERS < 191) {
	if (SELECTED_PRODUCT == 'RC112_ES112') {
		UAS_CPU = 4;
	} else {
		UAS_CPU = 3;
	}
} else {
	UAS_CPU = -1;
}

