package com.senzit.evidencer.server.service;

import java.util.HashMap;

public interface UserService {

	int updatePassword(String userName,String password);
	HashMap<String,String> getUserProfile(String userName);
	void updateUserProfile(String userName,String firstName, String middleName,
			String lastName, String secMail, String secMobile);
	String getUserRoleName(String userName);
	boolean checkDuplicatePrimaryDetails(String userName,String emailId,String ph);
}
