package com.senzit.evidencer.server.model;

import java.util.Date;

public class UserPrivilege {
		
	private int userPrivilegeId;
	private User user;
	private Privilege privilege;
	private Date dateOfPurchase;
	private Date dateOfExpire;
	
	public UserPrivilege(){}

	public int getUserPrivilegeId() {
		return userPrivilegeId;
	}

	public void setUserPrivilegeId(int userPrivilegeId) {
		this.userPrivilegeId = userPrivilegeId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Privilege getPrivilege() {
		return privilege;
	}

	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}

	public Date getDateOfPurchase() {
		return dateOfPurchase;
	}

	public void setDateOfPurchase(Date dateOfPurchase) {
		this.dateOfPurchase = dateOfPurchase;
	}

	public Date getDateOfExpire() {
		return dateOfExpire;
	}

	public void setDateOfExpire(Date dateOfExpire) {
		this.dateOfExpire = dateOfExpire;
	}

}
