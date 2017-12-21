package com.senzit.evidencer.server.dao;

import java.util.Date;
import java.util.List;

import com.senzit.evidencer.server.model.CaseEventDetail;
import com.senzit.evidencer.server.model.CaseInfo;
import com.senzit.evidencer.server.model.CaseStatus;
import com.senzit.evidencer.server.model.CaseType;

public interface CaseDao {
	
	CaseInfo getCaseInfoObj(String caseNo);
	String getCaseStatus(String caseNo);
	Object[] getLatEventDetails(String caseNo);

	List<CaseType> getCaseTypeObj(String type);
	List<CaseStatus> getCaseStatusObj(String status);
	
	String insertNewCase(CaseInfo caseObj);
	Integer insertNewCaseEvent(CaseEventDetail caseEventObj);	

	CaseEventDetail getCaseEventDetailObj(int caseEventId);
	void updateCaseEventDetailObj(CaseEventDetail obj);
	
	List<CaseInfo> getLiveCaseObj();
	Object[] getLastEventDetail(CaseInfo caseObj);
	Object[] getCaseDetail(int caseEventId);
	
	Integer setAttachmentFlag(int caseEventDetailId);
	Integer setPrivateNoteFlag(int caseEventDetailId);

	//// Search & Player
	List<Integer> getSittingNumbers(String caseNo);
	List<Integer> getSittingNumbers(String caseNo,Date date);
	List<Integer> getSessionNumbers(String caseNo,int sittingNo);
	List<Integer> getSessionNumbers(String caseNo,int sittingNo,Date date);
	
	List<CaseInfo> getCaseFromDate(Date date);
	Integer getCaseTypeId(String caseType);
	
	List<Object[]> getCaseInfo(String hql, List<Object> paramList);
	
	Object[] getFullCaseDetails(String caseNo,int sittingNo,int sessionNo);

}
