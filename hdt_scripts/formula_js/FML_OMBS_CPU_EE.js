if(SELECTED_PRODUCT=='EE112')
{
if (EE_USERS == 25) {
		OMBS_CPU= 2;
} else if (EE_USERS == 75)
{
	OMBS_CPU = 3;
}
else
	{
		OMBS_CPU= -1;

}
}
else if(SELECTED_PRODUCT=='EE113')
{
if (EE_USERS == 3.75) {
		OMBS_CPU= 2;
} else if (EE_USERS == 13)
{
	OMBS_CPU = 3;
}
else
	{
		OMBS_CPU= -1;

}
}