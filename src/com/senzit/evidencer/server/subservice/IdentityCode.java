package com.senzit.evidencer.server.subservice;

public class IdentityCode {
	
	public static int generateRandomCode(){
		
		return (int)Math.round(Math.random()* 89999) + 10000;
	}

}
