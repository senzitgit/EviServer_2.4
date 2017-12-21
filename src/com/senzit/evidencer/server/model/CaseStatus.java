package com.senzit.evidencer.server.model;

public class CaseStatus {
	
	private int caseStatusId;
	private String status;
	private String statusDescription;
	
	public CaseStatus(){}

	public int getCaseStatusId() {
		return caseStatusId;
	}

	public void setCaseStatusId(int caseStatusId) {
		this.caseStatusId = caseStatusId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}	

}
