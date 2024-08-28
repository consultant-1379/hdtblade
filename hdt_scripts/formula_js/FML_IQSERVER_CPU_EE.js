if(SELECTED_PRODUCT=='EE112')
{
if (EE_USERS == 25) {
		IQSERVER_CPU = 2;
} else if (EE_USERS == 75)
{
	IQSERVER_CPU = 4;
}
else
	{
		IQSERVER_CPU= -1;

}
}
else if(SELECTED_PRODUCT=='EE113')
{
if (EE_USERS == 3.75) {
		IQSERVER_CPU = 2;
} else if (EE_USERS == 13)
{
	IQSERVER_CPU = 4;
}
else
	{
		IQSERVER_CPU= -1;

}
}