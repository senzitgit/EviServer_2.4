package com.senzit.evidencer.server.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.senzit.evidencer.server.dao.CaseDao;
import com.senzit.evidencer.server.dao.ParticipantDao;
import com.senzit.evidencer.server.model.CaseEventDetail;
import com.senzit.evidencer.server.model.CaseInfo;
import com.senzit.evidencer.server.model.Court;
import com.senzit.evidencer.server.model.EviProperty;
import com.senzit.evidencer.server.model.Participant;
import com.senzit.evidencer.server.subservice.EviService;
import com.senzit.evidencer.server.subservice.RebbonService;

public class RecordServiceImp implements RecordService {
	
	private CaseDao caseDao;
	private ParticipantDao participantDao;

	public void setCaseDao(CaseDao caseDao) {
		this.caseDao = caseDao;
	}

	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}

	@Override
	public Object[] checkCase(String caseNo) {
		
		String caseStatus=caseDao.getCaseStatus(caseNo);
		Properties prop=new Properties();
		Object[] obj={new Object(),new Object()};
		if(caseStatus==null){	//new case
			
			obj[0]="New Case";
			obj[1]=prop;
		}
		else if(caseStatus.toLowerCase().equals("closed")){	//closed case
			
			obj[0]="Closed Case";
			obj[1]=prop;
		}
		else{
			
			obj[0]="Existing Case";
			obj[1]=getLastEventDetails(caseNo);
		}
		return obj;
	}

	@Override
	public Properties getLastEventDetails(String caseNo) {
		
		Properties prop=new Properties();
		Object[] obj=caseDao.getLatEventDetails(caseNo);
		if(obj==null)
			return prop;
		prop.put("caseTitle", obj[0]);
		prop.put("caseDescription", obj[1]);
		prop.put("caseType", obj[2]);
		prop.put("sittingNo", obj[3]);
		prop.put("sessionNo", obj[4]);
		return prop;
	}

	@Override
	public Integer createNewCaseEvent(JSONObject jsonData) throws JSONException {
		
		// INSERT INTO CASE TABLE IF NEW CASE //
		
		String caseNo=jsonData.getString("caseNo");
		CaseInfo caseObj=caseDao.getCaseInfoObj(caseNo);
		if(caseObj==null){		// New Case
			
			caseObj=new CaseInfo(caseNo,
					jsonData.getString("caseTitle"),
					jsonData.getString("caseDescription"),
					caseDao.getCaseTypeObj(jsonData.getString("caseType")).get(0),
					caseDao.getCaseStatusObj("Active").get(0));
			caseDao.insertNewCase(caseObj);
		}
		
		
		// INSERT INTO CASEEVENTDETAIL TABLE //
		
		boolean confidential=jsonData.getBoolean("confidential");
		caseObj=new CaseInfo();
		caseObj.setCaseNo(jsonData.getString("caseNo"));
		Court courtObj=new Court();
		courtObj.setCourtId(1001);
		
		CaseEventDetail newEvent=new CaseEventDetail(
				jsonData.getInt("sittingNo"),
				jsonData.getInt("sessionNo"),
				new Date(),
				new Timestamp(System.currentTimeMillis()),
				confidential,
				caseObj,
				courtObj);
		Integer caseEventId=caseDao.insertNewCaseEvent(newEvent);
		
		
		// INSERT INTO PARTICIPANTS TABLE //
		
		JSONArray judgeArray  = jsonData.getJSONArray("judges");
		JSONArray lawyerArray = jsonData.getJSONArray("lawyers");
		JSONArray partArray   = jsonData.getJSONArray("participants");
		JSONArray otherArray  = jsonData.getJSONArray("others");

		CaseEventDetail caseEvent=new CaseEventDetail();
		caseEvent.setCaseEventDetailId(caseEventId);
		
		int size=judgeArray.length();
		for(int j=0;j<size;j++){
			
			Participant participant= new Participant();			
			participant.setCaseEventDetail(caseEvent);
			participant.setParticipantCategory("judge");
			participant.setParticipantName(judgeArray.get(j).toString());
			participantDao.insertParticipant(participant);
		}
		
		size=lawyerArray.length();
		for(int j=0;j<size;j++){
			
			Participant participant= new Participant();			
			participant.setCaseEventDetail(caseEvent);
			participant.setParticipantCategory("lawyer");
			participant.setParticipantName(lawyerArray.get(j).toString());
			participantDao.insertParticipant(participant);
		}
		
		size=partArray.length();
		for(int j=0;j<size;j++){
			
			Participant participant= new Participant();			
			participant.setCaseEventDetail(caseEvent);
			participant.setParticipantCategory("participant");
			participant.setParticipantName(partArray.get(j).toString());
			participantDao.insertParticipant(participant);
		}
		
		size=otherArray.length();
		for(int j=0;j<size;j++){
			
			Participant participant= new Participant();			
			participant.setCaseEventDetail(caseEvent);
			participant.setParticipantCategory("other");
			participant.setParticipantName(otherArray.get(j).toString());
			participantDao.insertParticipant(participant);
		}
		
		return caseEventId;
	}
	
	@Override
	public void updateDb(String caseNo,int caseEventId, JSONObject jsonData,String toneAnalyze, String sentimentalAnalyze, String initiatedFrom) throws JSONException {
		
		JSONArray logNoteArray;
		logNoteArray = jsonData.getJSONArray("logNotes");
		CaseEventDetail eventObj=caseDao.getCaseEventDetailObj(caseEventId);
		String ftpPath="Evidencer/"+caseNo+"/Sitting "+eventObj.getSittingNo()+"/Session "+eventObj.getSessionNo();
		
		System.err.println("toneAnalyze :"+ toneAnalyze);
		System.err.println("sentimentalAnalyze : "+ sentimentalAnalyze);
		
		
		
		eventObj.setFtpLocation(ftpPath);
		eventObj.setLogNotes(logNoteArray.toString());
		eventObj.setEndTime(new Timestamp(System.currentTimeMillis()));
		eventObj.setToneAnalyze(toneAnalyze);
		eventObj.setSentimentalAnalyze(sentimentalAnalyze);
		eventObj.setInitiatedFrom(initiatedFrom);
		
		caseDao.updateCaseEventDetailObj(eventObj);
	}

	@Override
	public List<Hashtable<String, Object>> getLiveSessionDetails() {
		
		List<Hashtable<String, Object>> result=new ArrayList<Hashtable<String, Object>>();
		List<CaseInfo> caseInfoList=caseDao.getLiveCaseObj();
		int length=caseInfoList.size();
		for(int i=0;i<length;i++){
			
			Object[] objArray=caseDao.getLastEventDetail(caseInfoList.get(i));
			if(objArray==null)
				continue;
			Hashtable<String, Object> table=new Hashtable<String, Object>();
			table.put("caseNo", ((CaseInfo)objArray[0]).getCaseNo());
			table.put("caseTitle", ((CaseInfo)objArray[0]).getCaseTitle());
			table.put("caseDesc", ((CaseInfo)objArray[0]).getCaseDescription());
			table.put("caseEventId", objArray[1]);
			table.put("sittingNo", objArray[2]);
			table.put("sessionNo", objArray[3]);
			table.put("startTime", (EviService.formatTime((Timestamp)objArray[4])));
			table.put("courtName", objArray[5]);
			
			result.add(table);
		}
		
		return result;
	}

	@Override
	public Hashtable<String, Object> getLiveCaseDetails(int caseEventId) {
		
		RebbonService rebbonObj=new RebbonService();
		Properties mediaLinks=rebbonObj.getMediaLinks();
		
		if(mediaLinks==null)
			return null;
		List<Object[]> participantList=participantDao.getParticipants(caseEventId);
		if(participantList==null)
			return null;
		
		Hashtable<String, Object> result=new Hashtable<String, Object>();		
		Hashtable<String, ArrayList<String>> table=new Hashtable<String, ArrayList<String>>();
		
		Hashtable<String,Object> temp=new Hashtable<String,Object>();
		Object[] caseDetailArray=caseDao.getCaseDetail(caseEventId);
		if(caseDetailArray==null)
			return null;
		temp.put("caseNo", ((CaseInfo)caseDetailArray[0]).getCaseNo());
		temp.put("caseTitle", ((CaseInfo)caseDetailArray[0]).getCaseTitle());
		temp.put("caseDesc", ((CaseInfo)caseDetailArray[0]).getCaseDescription());
		temp.put("sittingNo", (Integer)caseDetailArray[1]);
		temp.put("sessionNo", (Integer)caseDetailArray[2]);
		temp.put("startTime", EviService.formatTime((Timestamp)caseDetailArray[3]));
		
		
		ArrayList<String> judgeList=new ArrayList<String>();
		ArrayList<String> partList=new ArrayList<String>();
		ArrayList<String> lawyerList=new ArrayList<String>();
		ArrayList<String> otherList=new ArrayList<String>();
		
		for(Object[] partArray:participantList){
			
			if(partArray[1].equals("judge"))
				judgeList.add(partArray[0].toString());
			else if(partArray[1].equals("lawyer"))
				lawyerList.add(partArray[0].toString());
			else if(partArray[1].equals("participant"))
				partList.add(partArray[0].toString());
			else if(partArray[1].equals("other"))
				otherList.add(partArray[0].toString());
		}
		
		table.put("judges", judgeList);
		table.put("lawyers", lawyerList);
		table.put("participants", partList);
		table.put("others", otherList);		

		result.put("mediaLinks", mediaLinks);
		result.put("participants", table);
		result.put("caseDetails", temp);
		
		return result;
	}

}
