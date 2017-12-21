package com.senzit.evidencer.server.model;

public class MobUser {
	
	private String email;
	private String password;
	
	public MobUser(){}
	
	public MobUser(String email){this.email=email;}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	

}
