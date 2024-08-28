if(SELECTED_PRODUCT=='EE112')
{
 // figure out whcih configuriation is suited by comparing with the system test log value
if (EE_USERS == 25) {
		MEDIATION_CPU = 2;
} else if (EE_USERS == 75)
{
	MEDIATION_CPU = 4;
}
else
	{
		MEDIATION_CPU= -1;

}
}
else if(SELECTED_PRODUCT=='EE113')
{
 // figure out whcih configuriation is suited by comparing with the system test log value
if (EE_USERS == 3.75) {
		MEDIATION_CPU = 2;
} else if (EE_USERS == 13)
{
	MEDIATION_CPU = 4;
}
else
	{
		MEDIATION_CPU= -1;

}
}