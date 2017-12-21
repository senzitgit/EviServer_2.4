package com.senzit.evidencer.server.model;

import java.sql.Timestamp;

public class Attachment {
	
	private int attachmentId;
	private String fileName;
	private String attachmentName;
	private String attachmentDescription;
	private Timestamp createdOn;
	private User createdBy;
	private CaseEventDetail caseEventDetail;
	
	public Attachment(){}
	public Attachment(String fileName,String attachmentName,String attachmentDescription,
			Timestamp createdOn,User createdBy,CaseEventDetail eventDetail){
		
		this.fileName=fileName;
		this.attachmentName=attachmentName;
		this.attachmentDescription=attachmentDescription;
		this.createdOn=createdOn;
		this.createdBy=createdBy;
		this.caseEventDetail=eventDetail;
	}

	public int getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(int attachmentId) {
		this.attachmentId = attachmentId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public String getAttachmentDescription() {
		return attachmentDescription;
	}

	public void setAttachmentDescription(String attachmentDescription) {
		this.attachmentDescription = attachmentDescription;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public CaseEventDetail getCaseEventDetail() {
		return caseEventDetail;
	}

	public void setCaseEventDetail(CaseEventDetail caseEventDetail) {
		this.caseEventDetail = caseEventDetail;
	}
	

}
