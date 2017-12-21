package com.senzit.evidencer.server.model;

public class Role {
	
	private int roleId;
	private String roleName;
	private String roleDescription;
	
	public Role(){}
	
	Role(String roleName,String roleDescription){
		this.roleName=roleName;
		this.roleDescription=roleDescription;
	}
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDescription() {
		return roleDescription;
	}
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

}
