package com.senzit.evidencer.server.model;

public class CaseType {
	
	private int caseTypeId;
	private String caseType;
	private String typeDescription;
	
	public CaseType(){}

	public int getCaseTypeId() {
		return caseTypeId;
	}

	public void setCaseTypeId(int caseTypeId) {
		this.caseTypeId = caseTypeId;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getTypeDescription() {
		return typeDescription;
	}

	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}	

}
