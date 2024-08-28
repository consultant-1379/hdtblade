if(SELECTED_PRODUCT=='EE112')
{
if (EE_USERS == 25) {
		NAS_CPU= 3;
} else if (EE_USERS == 75)
{
	NAS_CPU = 4;
}
else
	{
		NAS_CPU= -1;

}
}
else if(SELECTED_PRODUCT=='EE113')
{
if (EE_USERS == 3.75) {
		NAS_CPU= 3;
} else if (EE_USERS == 13)
{
	NAS_CPU = 4;
}
else
	{
		NAS_CPU= -1;

}
}