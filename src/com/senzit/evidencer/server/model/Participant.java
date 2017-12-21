package com.senzit.evidencer.server.model;

public class Participant {
	
	private int participantId;
	private String participantName;
	private String participantCategory;	
	private CaseEventDetail caseEventDetail;
	
	public Participant(){}

	public int getParticipantId() {
		return participantId;
	}

	public void setParticipantId(int participantId) {
		this.participantId = participantId;
	}

	public String getParticipantName() {
		return participantName;
	}

	public void setParticipantName(String participantName) {
		this.participantName = participantName;
	}

	public String getParticipantCategory() {
		return participantCategory;
	}

	public void setParticipantCategory(String participantCategory) {
		this.participantCategory = participantCategory;
	}

	public CaseEventDetail getCaseEventDetail() {
		return caseEventDetail;
	}

	public void setCaseEventDetail(CaseEventDetail caseEventDetail) {
		this.caseEventDetail = caseEventDetail;
	}	

}
