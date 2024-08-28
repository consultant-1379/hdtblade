if(SELECTED_PRODUCT=='EE112')
{
 // figure out whcih configuriation is suited by comparing with the system test log value
if (EE_USERS == 25) {
		SWITCH_IO = 60;
} else if (EE_USERS == 75)
{
	SWITCH_IO = 75;
}
else
	{
		SWITCH_IO= -1;

}
}