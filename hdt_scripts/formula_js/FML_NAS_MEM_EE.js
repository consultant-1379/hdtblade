if(SELECTED_PRODUCT=='EE112')
{
if (EE_USERS == 25) {
		NAS_MEM= 64;
} else if (EE_USERS == 75)
{
	NAS_MEM = 96;
}
else
	{
		NAS_MEM= -1;

}
}
else if(SELECTED_PRODUCT=='EE113')
{
if (EE_USERS == 3.75) {
		NAS_MEM= 64;
} else if (EE_USERS == 13)
{
	NAS_MEM = 96;
}
else
	{
		NAS_MEM= -1;

}
}