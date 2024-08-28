//SWITCH ENIQ EVENTS Blade IO Calculation
 

var EE_AVG_EVENTS_SEC;// avg events parameter
//EE_SUB; // number of subscribers
//EE_MOB_DATA_SUB; //number of mobile data subscribers
//EE_SDS_SAV_RATIO; // parameter from parameter table,
//EE_BH; // output of this script, varaible
// caluclate the peak events per hour
EE_AVG_EVENTS_SEC = Math.round(EE_SUB *EE_MOB_DATA_SUB* EE_SDS_SAV_RATIO * EE_BH/ 3600) * 0.6;

if(SELECTED_PRODUCT=='EE112')
{
 // figure out whcih configuriation is suited by comparing with the system test log value
if (EE_AVG_EVENTS_SEC > 20000000) {
		BIS_IO = 50;
} else {
	BIS_IO = 55;
}
}