if(SELECTED_PRODUCT=='EE112')
{
if (EE_USERS == 25) {
		IQSERVER_MEM = 8;
} else if (EE_USERS == 75)
{
	IQSERVER_MEM = 16;
}
else
	{
		IQSERVER_MEM= -1;

}
}