// ADMIN CPU Formula
// Merged and revised.

var ADMIN_CPU;

switch (SELECTED_NETWORK) {
	case 'LR':
		var A_CPU = 7.1592 * Math.exp(0.0005 * LR_NODE)
		if (A_CPU > 1 && A_CPU < 9) {
			ADMIN_CPU = 1;
		} else if (A_CPU > 9 && A_CPU < 12) {
			ADMIN_CPU = 2;
		} else if (A_CPU > 12 && A_CPU < 56) {
			ADMIN_CPU = 3;
		} else if (A_CPU > 56 && A_CPU < 96) {
			ADMIN_CPU = 4;
		} else {
			ADMIN_CPU = -1;
		}
		break;
	case 'WR':
		A_CPU =0.0014 * WR_CELL + 8.0511;
		
		if (A_CPU > 1 && A_CPU < 10) {
			ADMIN_CPU = 1;
		} else if (A_CPU > 10 && A_CPU < 15) {
			ADMIN_CPU = 2;
		} else if (A_CPU > 15 && A_CPU < 21) {
			ADMIN_CPU = 3;
		} else if (A_CPU > 21 && A_CPU < 60) {
			ADMIN_CPU = 4;
		} else {
			ADMIN_CPU = -1;
		}
		break;
	default:
		// This is to indicate an error.
		ADMIN_CPU = -1.0;
}