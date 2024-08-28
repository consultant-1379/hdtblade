if(SELECTED_PRODUCT=='EE112')
{
if (EE_USERS == 25) {
		IQSERVER_IO = 5;
} else if (EE_USERS == 75)
{
	IQSERVER_IO = 10;
}
else
	{
		IQSERVER_IO= -1;

}
}
else if(SELECTED_PRODUCT=='EE113')
{
if (EE_USERS == 3.75) {
		IQSERVER_IO = 5;
} else if (EE_USERS == 13)
{
	IQSERVER_IO = 10;
}
else
	{
		IQSERVER_IO= -1;

}
}