if(SELECTED_PRODUCT=='EE112')
{
if (EE_USERS == 25) {
		DAE_IO= 10;
} else if (EE_USERS == 75)
{
	DAE_IO = 11;
}
else
	{
		DAE_IO= -1;

}
}
else if(SELECTED_PRODUCT=='EE113')
{
if (EE_USERS == 3.75) {
		DAE_IO= 10;
} else if (EE_USERS == 13)
{
	DAE_IO = 11;
}
else
	{
		DAE_IO= -1;

}
}