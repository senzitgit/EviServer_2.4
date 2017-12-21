package com.senzit.evidencer.server.model;

import java.sql.Timestamp;

public class UserSession {
	
	private String userName;
	private User user;
	private String sessionId;
	private String clientIp;
	private Timestamp loginTime;
	private Timestamp lastRequestedTime;
	
	public UserSession(){}	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public Timestamp getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}

	public Timestamp getLastRequestedTime() {
		return lastRequestedTime;
	}

	public void setLastRequestedTime(Timestamp lastRequestedTime) {
		this.lastRequestedTime = lastRequestedTime;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
