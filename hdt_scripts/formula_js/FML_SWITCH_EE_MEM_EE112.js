

if(SELECTED_PRODUCT=='EE112')
{
 // figure out whcih configuriation is suited by comparing with the system test log value
if (EE_USERS == 25) {
		SWITCH_MEM = 16;
} else if (EE_USERS == 75)
{
	SWITCH_MEM = 24;
}
else
	{
		SWITCH_MEM= -1;

}
}