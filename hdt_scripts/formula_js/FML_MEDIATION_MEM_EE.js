if(SELECTED_PRODUCT=='EE112')
{
if (EE_USERS == 25) {
		MEDIATION_MEM = 8;
} else if (EE_USERS == 75)
{
	MEDIATION_MEM = 16;
}
else
	{
		MEDIATION_MEM= -1;

}
}
else if(SELECTED_PRODUCT=='EE113')
{
if (EE_USERS == 3.75) {
		MEDIATION_MEM = 8;
} else if (EE_USERS == 13)
{
	MEDIATION_MEM = 16;
}
else
	{
		MEDIATION_MEM= -1;

}
}