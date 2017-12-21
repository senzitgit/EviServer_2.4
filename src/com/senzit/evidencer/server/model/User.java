package com.senzit.evidencer.server.model;

import java.sql.Timestamp;

public class User {

	private String userName;
	private String password;
	private String firstName;
	private String middleName;
	private String lastName;
	private String primaryEmailId;
	private String secondaryEmailId;
	private String primaryMobileNumber;
	private String secondaryMobileNumber;
	private Byte userType;		// for admin 0 (ZERO)
	private Timestamp lastModifiedOn;
	private Timestamp createdOn;
	private String createdBy;
	private Role userRole;		//one-to-one association with ROLE table
	
	public User(){}
	
	public User(String userName,String password,String firstName,String primaryEmailId,
			String primaryMobileNumber){
		this.firstName=firstName;
		this.userName=userName;
		this.password=password;
		this.primaryEmailId=primaryEmailId;
		this.primaryMobileNumber=primaryMobileNumber;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPrimaryEmailId() {
		return primaryEmailId;
	}
	public void setPrimaryEmailId(String primaryEmailId) {
		this.primaryEmailId = primaryEmailId;
	}
	public String getSecondaryEmailId() {
		return secondaryEmailId;
	}
	public void setSecondaryEmailId(String secondaryEmailId) {
		this.secondaryEmailId = secondaryEmailId;
	}
	public String getPrimaryMobileNumber() {
		return primaryMobileNumber;
	}
	public void setPrimaryMobileNumber(String primaryMobileNumber) {
		this.primaryMobileNumber = primaryMobileNumber;
	}
	public String getSecondaryMobileNumber() {
		return secondaryMobileNumber;
	}
	public void setSecondaryMobileNumber(String secondaryMobileNumber) {
		this.secondaryMobileNumber = secondaryMobileNumber;
	}
	public Byte getUserType() {
		return userType;
	}
	public void setUserType(Byte userType) {
		this.userType = userType;
	}
	public Timestamp getLastModifiedOn() {
		return lastModifiedOn;
	}
	public void setLastModifiedOn(Timestamp lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}
	public Timestamp getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Role getUserRole() {
		return userRole;
	}
	public void setUserRole(Role userRole) {
		this.userRole = userRole;
	}

}
