if(SELECTED_PRODUCT=='EE112')
{
 // figure out whcih configuriation is suited by comparing with the system test log value
if (EE_USERS == 3.75) 
	{
		PRESENTATION_MEM = 8;
    }
	else if (EE_USERS == 13)
    {
	PRESENTATION_MEM = 16;
    }
    else
	{
		PRESENTATION_MEM= -1;
    }
}
else if(SELECTED_PRODUCT=='EE113')
{
 // figure out whcih configuriation is suited by comparing with the system test log value
if (EE_USERS == 3.75) 
	{
		PRESENTATION_MEM = 8;
    }
	else if (EE_USERS == 13)
    {
	PRESENTATION_MEM = 16;
    }
    else
	{
		PRESENTATION_MEM= -1;
    }
}

