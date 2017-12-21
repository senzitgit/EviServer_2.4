package com.senzit.evidencer.server.service;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface RegService {
	
	boolean isValidDomain(String emailId);
	long isValidUserParams(String userName,String primaryEmailId);
	String getDefaultLocation();
	void sendNotification(String firstName, String primaryEmailId, String primaryMobileNumber,
			int emailCode, int smsCode)throws AddressException, IOException, MessagingException;
	void saveUserProfile(String userName, String password, String firstName,
			String middleName, String lastName,String primaryEmailId, String primaryMobileNo,
			String secondaryEmailId, String secondaryMobileNumber, String securityQn1,
			String securityAns1, String securityQn2, String securityAns2,
			String securityQn3, String securityAns3);
	boolean notifyAdmin(String userName)throws AddressException, IOException, MessagingException;
	
	List<Hashtable<String, Object>> getPartialRegisters();
	void confirmRegistration(String userName,Byte type,Integer roleId,List<Integer> privilegeIdJson,
			boolean acceptOrReject);
	
}
