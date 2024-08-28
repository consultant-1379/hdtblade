if(SELECTED_PRODUCT=='EE112')
{
 // figure out whcih configuriation is suited by comparing with the system test log value
if (EE_USERS == 25) {
		SWITCH_CPU = 2;
} else if (EE_USERS == 75)
{
	SWITCH_CPU = 3;
}
else
	{
		SWITCH_CPU= -1;

}
}
else if(SELECTED_PRODUCT=='EE113')
{
 // figure out whcih configuriation is suited by comparing with the system test log value
if (EE_USERS == 3.75) {
		SWITCH_CPU = 2;
} else if (EE_USERS == 13)
{
	SWITCH_CPU = 3;
}
else
	{
		SWITCH_CPU= -1;

}
}