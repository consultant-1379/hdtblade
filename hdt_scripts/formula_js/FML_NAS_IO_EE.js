if(SELECTED_PRODUCT=='EE112')
{
if (EE_USERS == 25) {
		NAS_IO= 60;
} else if (EE_USERS == 75)
{
	NAS_IO = 75;
}
else
	{
		NAS_IO= -1;

}
}
else if(SELECTED_PRODUCT=='EE113')
{
if (EE_USERS == 3.75) {
		NAS_IO= 60;
} else if (EE_USERS == 13)
{
	NAS_IO = 75;
}
else
	{
		NAS_IO= -1;

}
}