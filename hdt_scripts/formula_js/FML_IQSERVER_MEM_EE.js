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
else if(SELECTED_PRODUCT=='EE113')
{
if (EE_USERS == 3.75) {
		IQSERVER_MEM = 8;
} else if (EE_USERS == 13)
{
	IQSERVER_MEM = 16;
}
else
	{
		IQSERVER_MEM= -1;

}
}