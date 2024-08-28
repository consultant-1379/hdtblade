if(SELECTED_PRODUCT=='EE112')
{
if (EE_USERS == 25) {
		MWS_MEM = 24;
} else if (EE_USERS == 75)
{
	MWS_MEM = 32;
}
else
	{
		MWS_MEM= -1;

}
}
else if(SELECTED_PRODUCT=='EE113')
{
if (EE_USERS == 3.75) {
		MWS_MEM = 24;
} else if (EE_USERS == 13)
{
	MWS_MEM = 32;
}
else
	{
		MWS_MEM= -1;

}
}