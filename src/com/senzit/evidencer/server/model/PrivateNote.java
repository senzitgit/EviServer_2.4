package com.senzit.evidencer.server.model;

import java.sql.Timestamp;

public class PrivateNote {
	
	private int privateNoteId;
	private String privateNotes;
	private Timestamp createdOn;
	private User createdBy;	
	private CaseEventDetail caseEventDetail;
	
	public PrivateNote(){}

	public int getPrivateNoteId() {
		return privateNoteId;
	}

	public void setPrivateNoteId(int privateNoteId) {
		this.privateNoteId = privateNoteId;
	}

	public String getPrivateNotes() {
		return privateNotes;
	}

	public void setPrivateNotes(String privateNotes) {
		this.privateNotes = privateNotes;
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
