package com.senzit.evidencer.server.model;

public class Privilege {
	
	private int privilegeId;
	private String privilege;
	
	public Privilege(){}

	public int getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(int privilegeId) {
		this.privilegeId = privilegeId;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

}
