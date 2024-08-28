if(SELECTED_PRODUCT=='EE112')
{
 // figure out whcih configuriation is suited by comparing with the system test log value
if (EE_USERS == 25) {
		PRESENTATION_CPU = 2;
} else if (EE_USERS == 75)
{
	PRESENTATION_CPU = 4;
}
else
	{
		PRESENTATION_CPU= -1;

}
}