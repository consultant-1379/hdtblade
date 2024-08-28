//var     EE_USERS=1.5; //Delete this,  it comes from engine.
var		SDS_TO_SAU	 =	0.85
var		MOB_DATA_PERCENTAGE	 =	0.98
var WEEKDAY_BH_FOR_24_HOURS=[0,2.9,1.9,1.2,0.7,0.6,0.6,0.8,1.5,3.2,5,6.2,7,6.5,6.9,6.1,5.9,5.8,5.2,5.1,5.1,5.3,5.2,6.4,4.9];

//Ignore the first postion its to make array indexing from 1 to 24.
var WEEKEND_BH_FOR_24_HOURS=[0,3.7,2.8,2,1.5,1.3,1.3,1.4,1.4,1.9,3.8,3.8,4.9,5.7,5.8,5.8,5.9,6,6,5.9,5.9,6.1,6.3,5.9,4.9];

var		SPEECH_TRAFFIC_CS	 =	16
var		SPEECH_TRAFFIC_ORIGINATING	 =	1.25
var		SPEECH_TRAFFIC_TERMINATING	 =	1.5
var		TERMINATING_SMS_CS	 =	0.25
var		ORIGINATING_SMS_CS	 =	0.1
var		BH_TRAFFIC_VOLUMES_DAILY	 =	0.1
var     Event_Rate_BH


//totalAverageEventRatePerSecond();
function totalAverageEventRatePerSecond()
{
	var TOTAL_EVENTS_PER_SUB_DURING_BH=
{
	GSM:3.576,
	WCDMA:5.748,// the calculation for these constants is not included in the formula.
	LTE:13.29
};  
var NETWORK_PERCENTAGES=
{
	GSM:0.33,
	WCDMA:0.33,//these will be input parameter in the future.
    LTE:0.34
}; 
	var PERCENTAGE_MOBILES_DATA_PER_SUB=
		{
	GSM:0.98,
	WCDMA:0.98,
    LTE:0.98
}; 

var FEATURES_PERCENTAGE=
		{
	VOICESMS:0.0
}; 



//Ignore the first postion its to make array indexing from 1 to 24.
 var sumOfNetworkEvents=0;
	for(var networks in TOTAL_EVENTS_PER_SUB_DURING_BH) 
	{
		
 		   sumOfNetworkEvents+= getAverageEventRatePerSecond(TOTAL_EVENTS_PER_SUB_DURING_BH[networks],NETWORK_PERCENTAGES[networks]);

	}

  var dvThroughPut=  getDVThroughPut(PERCENTAGE_MOBILES_DATA_PER_SUB,NETWORK_PERCENTAGES);
 var voiceAndSMS= getVoiceAndSMS(FEATURES_PERCENTAGE);
sumOfNetworkEvents=sumOfNetworkEvents+dvThroughPut+voiceAndSMS;

return sumOfNetworkEvents;

}

function getVoiceAndSMS(featurePercentage)
{
	
	var speechTrafficPerCSSubscriber60=SPEECH_TRAFFIC_CS*0.6;
	var speechTrafficPerCSSubscriber40=SPEECH_TRAFFIC_CS*0.4;
	var mobileOriginatingCallArrivalBH=(speechTrafficPerCSSubscriber60*60)/(SPEECH_TRAFFIC_ORIGINATING*1000)
	var mobileTerminatingCallArrivalBH= (speechTrafficPerCSSubscriber40*60)/(SPEECH_TRAFFIC_TERMINATING*1000)
    var moblieOriginatingVoiceCDRPerBH=EE_USERS*1000000*featurePercentage.VOICESMS*mobileOriginatingCallArrivalBH
    var moblieTerminatingVoiceCDRPerBH=EE_USERS*1000000*featurePercentage.VOICESMS*mobileTerminatingCallArrivalBH
    var CFBusy=0.39*mobileTerminatingCallArrivalBH*0.3
    var CFNotAnswered=0.61*mobileTerminatingCallArrivalBH*0.35*0.3
	var CFCDRBH=(EE_USERS*1000000*featurePercentage.VOICESMS)*(CFBusy+CFNotAnswered);
	var mtSMS=EE_USERS*1000000*featurePercentage.VOICESMS*TERMINATING_SMS_CS
	var mOSMSCDR= EE_USERS*1000000*featurePercentage.VOICESMS*ORIGINATING_SMS_CS
    var totalCDRPerBH= moblieOriginatingVoiceCDRPerBH+moblieTerminatingVoiceCDRPerBH+CFCDRBH+mtSMS+mOSMSCDR
	var totalCDRPerSec=totalCDRPerBH/3600;
	var dailyCDRVolume=totalCDRPerBH/BH_TRAFFIC_VOLUMES_DAILY;
	var averageCDRPerSec=dailyCDRVolume/86400
	return averageCDRPerSec;


}

function getDVThroughPut(percentageOfMobilesDataPerSub,percentageOfNetwork)
{
 var dvThroughPut=Math.round((EE_USERS*1000000*percentageOfNetwork.GSM*percentageOfMobilesDataPerSub.GSM+EE_USERS*1000000*percentageOfNetwork.WCDMA*percentageOfMobilesDataPerSub.WCDMA)*0.9*0.000324074+(EE_USERS*1000000*percentageOfNetwork.GSM*percentageOfMobilesDataPerSub.GSM+EE_USERS*1000000*percentageOfNetwork.WCDMA*percentageOfMobilesDataPerSub.WCDMA)*0.1*0.001851852);
 	return dvThroughPut;
}


function getAverageEventRatePerSecond( eventsPerSubDuringBH, percentageOfTheNetwork)
{ 
	var totalEventsDuringBH= getTotalEventsDuringBH(eventsPerSubDuringBH,percentageOfTheNetwork); //step 2
	var eventRateBH=getEventRatePerSecDuringBH(totalEventsDuringBH);
	var WEEKDAY_EVENT_RATE_PER_SECOND_FOR_EACH_HOUR=new Array();
    WEEKDAY_EVENT_RATE_PER_SECOND_FOR_EACH_HOUR[0]=0;
for(var i=1;i<=24;i++)
{
WEEKDAY_EVENT_RATE_PER_SECOND_FOR_EACH_HOUR[i]=Math.round((eventRateBH/WEEKDAY_BH_FOR_24_HOURS[12])*WEEKDAY_BH_FOR_24_HOURS[i]);

}

var sumOfAllTheElementsInArray=addAllTheElementsOFArray(WEEKDAY_EVENT_RATE_PER_SECOND_FOR_EACH_HOUR);
var averageEventRate =Math.round(sumOfAllTheElementsInArray/24);
 return averageEventRate;
	 

}



function getTotalEventsDuringBH(eventsPerSubDuringBH,percentageOfTheNetwork)
{
	var totalEventsDuringBH;
	totalEventsDuringBH=EE_USERS*1000000*percentageOfTheNetwork*MOB_DATA_PERCENTAGE*SDS_TO_SAU*eventsPerSubDuringBH;
	return totalEventsDuringBH;


}

function getEventRatePerSecDuringBH(totalEventsDuringBH) 
{
	//3. Event rate/sec during BH
var eventRateBH=Math.round(totalEventsDuringBH/3600);
return eventRateBH;

}


 function addAllTheElementsOFArray(o){
    for(var s = 0, i = o.length; i; s += o[--i]);
    return s;
};

if(totalAverageEventRatePerSecond()>5080)
{
 	//4 blade
	SWITCH_MEM=16;
	PRESENTATION_MEM=8
		MEDIATION_MEM=8
		COORDINATOR_MEM=8
		IQSERVER_MEM=8
		MWS_MEM=24
		DAE_MEM=288
		NAS_MEM=64
		STORAGE_MEM=16
		OMBS_MEM=16
}
else 
{
 
    //6 blade
	SWITCH_MEM=24;
	PRESENTATION_MEM=16
		MEDIATION_MEM=16
		COORDINATOR_MEM=16
		IQSERVER_MEM=16
		MWS_MEM=32
		DAE_MEM=320
		NAS_MEM=96
		STORAGE_MEM=24
		OMBS_MEM=24
}