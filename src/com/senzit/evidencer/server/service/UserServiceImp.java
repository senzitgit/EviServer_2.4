package com.senzit.evidencer.server.service;

import java.util.HashMap;

import com.senzit.evidencer.server.dao.SecurityQuestionDao;
import com.senzit.evidencer.server.dao.UserDao;
import com.senzit.evidencer.server.model.SecurityQuestion;
import com.senzit.evidencer.server.model.User;

public class UserServiceImp implements UserService {
	
	private UserDao userDao;
	private SecurityQuestionDao qnDao;

	public void setQnDao(SecurityQuestionDao qnDao) {
		this.qnDao = qnDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public int updatePassword(String userName, String password) {
		
		return userDao.updatePassword(userName, password);
	}

	@Override
	public HashMap<String,String> getUserProfile(String userName) {
		
		User user= userDao.getUser(userName);
		SecurityQuestion qn=qnDao.getSecurityQuestionObj(userName);
		HashMap<String,String> profileTable=new HashMap<String,String>();
		
		if(user==null || qn==null)
			return profileTable;
		
		profileTable.put("firstName",user.getFirstName());
		
		String middleName = user.getMiddleName();
		profileTable.put("middleName",(middleName==null)?"":middleName);
		
		String lastName = user.getLastName();
		profileTable.put("lastName",(lastName==null)?"":lastName);
		
		profileTable.put("primaryEmailId", user.getPrimaryEmailId());
		profileTable.put("primaryMobileNo", user.getPrimaryMobileNumber());
		
		String emailAddress = user.getSecondaryEmailId();
		profileTable.put("emailAddress",(emailAddress==null)?"":emailAddress);
		
		String mobileNumber = user.getSecondaryMobileNumber();
		profileTable.put("mobileNumber",(mobileNumber==null)?"":mobileNumber);
		
		profileTable.put("securityQuestion1",qn.getSecurityQuestion1());
		profileTable.put("securityAnswer1",qn.getSecurityAnswer1());
		profileTable.put("securityQuestion2",qn.getSecurityQuestion2());
		profileTable.put("securityAnswer2",qn.getSecurityAnswer2());
		profileTable.put("securityQuestion3",qn.getSecurityQuestion3());
		profileTable.put("securityAnswer3",qn.getSecurityAnswer3());
		
		return profileTable;
	}

	@Override
	public void updateUserProfile(String userName,String firstName, String middleName,
			String lastName, String secMail, String secMobile) {
		
		User userObj=userDao.getUser(userName);
		userObj.setFirstName(firstName);
		userObj.setMiddleName(middleName);
		userObj.setLastName(lastName);
		userObj.setSecondaryEmailId(secMail);
		userObj.setSecondaryMobileNumber(secMobile);
		
		userDao.updateUser(userObj);
	}

	@Override
	public String getUserRoleName(String userName) {
		
		String temp= userDao.getUserRoleName(userName);
		if (temp==null)
			return "";
		return temp;
	}

	@Override
	public boolean checkDuplicatePrimaryDetails(String userName, String emailId,
			String ph) {
		
		Object[] objArray = userDao.getUserPrimaryDetails(userName);
		if(objArray[1].equals(emailId) || objArray[2].equals(ph))
			return false;
		return true;
	}

}
