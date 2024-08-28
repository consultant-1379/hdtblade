if(SELECTED_PRODUCT=='EE112')
{
if (EE_USERS == 25) {
		MWS_CPU = 2;
} else if (EE_USERS == 75)
{
	MWS_CPU = 3;
}
else
	{
		MWS_CPU= -1;

}
}
else if(SELECTED_PRODUCT=='EE113')
{
if (EE_USERS == 3.75) {
		MWS_CPU = 2;
} else if (EE_USERS == 13)
{
	MWS_CPU = 3;
}
else
	{
		MWS_CPU= -1;

}
}