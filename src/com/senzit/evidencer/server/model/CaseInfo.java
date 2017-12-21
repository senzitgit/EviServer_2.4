package com.senzit.evidencer.server.model;

public class CaseInfo {
	
	private String caseNo;
	private String caseTitle;
	private String caseDescription;
	private CaseType caseType;
	private CaseStatus caseStatus;
	
	public CaseInfo(){}
	public CaseInfo(String caseNo,String caseTitle,String caseDescription,
			CaseType caseType,CaseStatus caseStatus){
		
		this.caseNo=caseNo;
		this.caseTitle=caseTitle;
		this.caseDescription=caseDescription;
		this.caseType=caseType;
		this.caseStatus=caseStatus;
	}

	public String getCaseNo() {
		return caseNo;
	}

	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}

	public String getCaseTitle() {
		return caseTitle;
	}

	public void setCaseTitle(String caseTitle) {
		this.caseTitle = caseTitle;
	}

	public String getCaseDescription() {
		return caseDescription;
	}

	public void setCaseDescription(String caseDescription) {
		this.caseDescription = caseDescription;
	}

	public CaseType getCaseType() {
		return caseType;
	}

	public void setCaseType(CaseType caseType) {
		this.caseType = caseType;
	}

	public CaseStatus getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(CaseStatus caseStatus) {
		this.caseStatus = caseStatus;
	}	

}
