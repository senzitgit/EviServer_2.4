package com.senzit.evidencer.server.model;

public class LandEPath {
	
	private String userName;
	private User user;
	private String lePath;
	
	public LandEPath(){}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLePath() {
		return lePath;
	}

	public void setLePath(String lePath) {
		this.lePath = lePath;
	}

}
