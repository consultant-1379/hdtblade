if(SELECTED_PRODUCT=='EE112')
{
 // figure out whcih configuriation is suited by comparing with the system test log value
if (EE_USERS == 25) {
		COORDINATOR_MEM = 8;
} else if (EE_USERS == 75)
{
	COORDINATOR_MEM = 16;
}
else
	{
		COORDINATOR_MEM= -1;

}
}