if(SELECTED_PRODUCT=='EE112')
{
if (EE_USERS == 25) {
		OMBS_MEM= 16;
} else if (EE_USERS == 75)
{
	OMBS_MEM = 24;
}
else
	{
		OMBS_MEM= -1;

}
}
else if(SELECTED_PRODUCT=='EE113')
{
if (EE_USERS == 3.75) {
		OMBS_MEM= 16;
} else if (EE_USERS == 13)
{
	OMBS_MEM = 24;
}
else
	{
		OMBS_MEM= -1;

}
}