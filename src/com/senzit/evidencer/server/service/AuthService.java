package com.senzit.evidencer.server.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface AuthService {
	
	boolean authenticate(String userName,String password);
	HashMap<String,Object> getLoginResults(String userName);
	boolean validateEmail(String userName,String emailId);
	Properties getRandomQuestions(String userName);
	boolean verifySecurityQuestionAndAnswer(String userName,
			String securityQuestion1, String securityAnswer1,
			String securityQuestion2, String securityAnswer2);
	void sendToken(String userName,int token,byte smsOrEmail)throws AddressException, IOException, MessagingException;
	boolean isSamePassword(String userName, String password);
	long checkPrivateNotePrivilege(String userName);

}
