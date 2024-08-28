
if(SELECTED_PRODUCT=='EE112')
{
 // figure out whcih configuriation is suited by comparing with the system test log value
if (EE_SUB <= 3) {
		BIS_MEM = 104;
} else if (EE_SUB >=3 && EE_SUB <=25)
{
	BIS_MEM = 112;
}
else
	{
		BIS_MEM= -1;

}
}