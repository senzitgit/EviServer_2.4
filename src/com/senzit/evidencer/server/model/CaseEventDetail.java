package com.senzit.evidencer.server.model;

import java.sql.Timestamp;
import java.util.Date;

public class CaseEventDetail {
	
	private int caseEventDetailId;
	private int sittingNo;
	private int sessionNo;
	private Date eventDate;
	private String logNotes;//Can use clob type in java.sql
	private String toneAnalyze;
	private String sentimentalAnalyze;
	private String initiatedFrom;
	private String linkCrime;
	private String ftpLocation;
	private Timestamp startTime;
	private Timestamp endTime;
	private boolean privateNoteFlag;
	private boolean attachmentFlag;
	private boolean confidential;
	
	private CaseInfo caseId;//case is reserved word
	private Court court;
	
	public CaseEventDetail(){}
	
	public CaseEventDetail(int sittingNo,int sessionNo,Date eventDate,Timestamp startTime,
			boolean confidential,CaseInfo caseId,Court court){
		
		this.sittingNo=sittingNo;
		this.sessionNo=sessionNo;
		this.eventDate=eventDate;
		this.startTime=startTime;
		this.confidential=confidential;
		this.caseId=caseId;
		this.court=court;
		this.attachmentFlag=false;
		this.privateNoteFlag=false;
	}
	
	public int getCaseEventDetailId() {
		return caseEventDetailId;
	}
	public void setCaseEventDetailId(int caseEventDetailId) {
		this.caseEventDetailId = caseEventDetailId;
	}
	public int getSittingNo() {
		return sittingNo;
	}
	public void setSittingNo(int sittingNo) {
		this.sittingNo = sittingNo;
	}
	public int getSessionNo() {
		return sessionNo;
	}
	public void setSessionNo(int sessionNo) {
		this.sessionNo = sessionNo;
	}
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	public String getLogNotes() {
		return logNotes;
	}
	public void setLogNotes(String logNotes) {
		this.logNotes = logNotes;
	}
	public String getFtpLocation() {
		return ftpLocation;
	}
	public void setFtpLocation(String ftpLocation) {
		this.ftpLocation = ftpLocation;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public boolean getPrivateNoteFlag() {
		return privateNoteFlag;
	}
	public void setPrivateNoteFlag(boolean privateNoteFlag) {
		this.privateNoteFlag = privateNoteFlag;
	}
	public boolean getAttachmentFlag() {
		return attachmentFlag;
	}
	public void setAttachmentFlag(boolean attachmentFlag) {
		this.attachmentFlag = attachmentFlag;
	}
	public boolean getConfidential() {
		return confidential;
	}
	public void setConfidential(boolean confidential) {
		this.confidential = confidential;
	}
	public CaseInfo getCaseId() {
		return caseId;
	}
	public void setCaseId(CaseInfo caseId) {
		this.caseId = caseId;
	}
	public Court getCourt() {
		return court;
	}
	public void setCourt(Court court) {
		this.court = court;
	}

	public String getToneAnalyze() {
		return toneAnalyze;
	}

	public void setToneAnalyze(String toneAnalyze) {
		this.toneAnalyze = toneAnalyze;
	}

	public String getSentimentalAnalyze() {
		return sentimentalAnalyze;
	}

	public void setSentimentalAnalyze(String sentimentalAnalyze) {
		this.sentimentalAnalyze = sentimentalAnalyze;
	}

	public String getInitiatedFrom() {
		return initiatedFrom;
	}

	public void setInitiatedFrom(String initiatedFrom) {
		this.initiatedFrom = initiatedFrom;
	}

	public String getLinkCrime() {
		return linkCrime;
	}

	public void setLinkCrime(String linkCrime) {
		this.linkCrime = linkCrime;
	}	

}
