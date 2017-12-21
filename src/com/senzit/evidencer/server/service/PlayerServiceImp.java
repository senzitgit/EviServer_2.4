package com.senzit.evidencer.server.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.senzit.evidencer.server.dao.AttachmentDao;
import com.senzit.evidencer.server.dao.CaseDao;
import com.senzit.evidencer.server.dao.PrivateNoteDao;
import com.senzit.evidencer.server.model.CaseEventDetail;
import com.senzit.evidencer.server.model.CaseInfo;
import com.senzit.evidencer.server.subservice.EviService;
import com.senzit.evidencer.server.subservice.RebbonService;

public class PlayerServiceImp implements PlayerService {
	
	private CaseDao caseDao;
	private AttachmentDao attachmentDao;
	private PrivateNoteDao privateDao;

	public void setPrivateDao(PrivateNoteDao privateDao) {
		this.privateDao = privateDao;
	}

	public void setAttachmentDao(AttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}

	public void setCaseDao(CaseDao caseDao) {
		this.caseDao = caseDao;
	}

	@Override
	public  HashMap<String,Object> getCaseEventDetails(String caseNo, int sittingNo,
			int sessionNo) {
		
		Object[] obj=caseDao.getFullCaseDetails(caseNo, sittingNo, sessionNo);
		if(obj==null)
			return null;
		HashMap<String,Object> result=new  HashMap<String,Object>();
//		obj[0] is CaseInfo/ obj[1] is CaseEventDetail
		CaseInfo caseObj=(CaseInfo)obj[0];
		CaseEventDetail eventObj=(CaseEventDetail)obj[1];
		
		result.put("caseEventId", eventObj.getCaseEventDetailId());
		result.put("caseTitle", caseObj.getCaseTitle());
		result.put("caseDesc", caseObj.getCaseDescription());
		result.put("eventDate", eventObj.getEventDate());
		result.put("logNotes", eventObj.getLogNotes());
		result.put("startTime", EviService.formatTime(eventObj.getStartTime())  );
		String endT=(eventObj.getEndTime()==null)?null:EviService.formatTime(eventObj.getEndTime());
		result.put("endTime", endT);
		result.put("privateNoteFlag", eventObj.getPrivateNoteFlag());
		result.put("attachmentFlag", eventObj.getAttachmentFlag());
		result.put("toneAnalyze", eventObj.getToneAnalyze());
		result.put("sentimentalAnalyze", eventObj.getSentimentalAnalyze());
		result.put("initiatedFrom", eventObj.getInitiatedFrom());
		result.put("confidential", eventObj.getConfidential());
		
		return result;
	}

	@Override
	public List<Hashtable<String, Object>> getAttachmentDetails(int caseEventId,String userName) throws JSONException {
		
		RebbonService temp=new RebbonService();
		JSONObject linkJson=temp.getAttachmentLinks(caseEventId,userName);
		if(linkJson==null)
			return null;
		String commonLink=linkJson.getString("Attachments");
		List<Object[]> objArrayList=attachmentDao.getAttachmentInfo(caseEventId);
		int length=objArrayList.size();
		List<Hashtable<String, Object>> result= new ArrayList<Hashtable<String, Object>>();
		for(int i=0;i<length;i++){			
			
			Hashtable<String, Object> table=new Hashtable<String, Object>();
			Object[] objArray=objArrayList.get(i);
			String fileName=(String)objArray[0];
			table.put("fileLink", commonLink+fileName);
			table.put("attachmentName", objArray[1]);
			table.put("attachmentDescription", objArray[2]);
			table.put("createdOn", EviService.formatTime((Timestamp) objArray[3]));
			table.put("userName", objArray[4]);
			table.put("firstName", objArray[5]);
			
			result.add(table);
			
		}
		return result;
	}

	@Override
	public List<Hashtable<String, Object>> getPrivateNoteDetails(int caseEventId) {
		
		List<Object[]> noteInfoList=privateDao.getPrivateNoteInfo(caseEventId);
		int length=noteInfoList.size();
		List<Hashtable<String, Object>> result=new ArrayList<Hashtable<String, Object>>();
		for(int i=0;i<length;i++){
			
			Hashtable<String, Object> table=new Hashtable<String, Object>();			
			Object[] noteInfoArray=noteInfoList.get(i);
			table.put("privateNote", noteInfoArray[0]);
			table.put("createdOn", EviService.formatTime((Timestamp)noteInfoArray[1]));
			table.put("userName", noteInfoArray[2]);
			table.put("firstName", noteInfoArray[3]);
			
			result.add(table);
			
		}
		return result;
	}

}
