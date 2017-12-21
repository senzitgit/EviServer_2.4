package com.senzit.evidencer.server.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.senzit.evidencer.server.dao.PrivilegeDao;
import com.senzit.evidencer.server.dao.SecurityQuestionDao;
import com.senzit.evidencer.server.dao.UserDao;
import com.senzit.evidencer.server.model.SecurityQuestion;
import com.senzit.evidencer.server.subservice.EmailSms;
import com.senzit.evidencer.server.subservice.EviService;

public class AuthServiceImp implements AuthService {
	
	private UserDao userDao;
	private PrivilegeDao privilegeDao;
	private SecurityQuestionDao qnDao;

	public void setQnDao(SecurityQuestionDao qnDao) {
		this.qnDao = qnDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setPrivilegeDao(PrivilegeDao privilegeDao) {
		this.privilegeDao = privilegeDao;
	}

	@Override
	public boolean authenticate(String userName, String password) {
		
		System.err.println("Pass"+userDao.getPassword(userName));
		return password.equals(userDao.getPassword(userName));
	}

	@Override
	public HashMap<String, Object> getLoginResults(String userName){
		
		List<String> privilegeList= new ArrayList<String>();	// NEED TO CHANGE
		List<Object[]> privileges = privilegeDao.getUserPrivileges(userName);
		////////////////////////////
		for(Object[] obj : privileges){
			
			privilegeList.add(obj[0].toString());
		}
		
		////////////////////////////////
		if(privilegeList!=null && !privilegeList.isEmpty()){
			
			List<String> allPrivilegeList=privilegeDao.getAllPrivileges();
			
			HashMap<String, Object> table = new HashMap<String, Object>();
				
			for(String privilege : allPrivilegeList){
				
				boolean flag=false;
				for(String userPrivilege : privilegeList)
					if(privilege.equals(userPrivilege)){
						
						table.put(privilege, "1");
						flag=true;
						privilegeList.remove(userPrivilege);
						break;
					}
				if(!flag)
					table.put(privilege, "0");
			}
			
			Object[] nameArray=userDao.getUserNames(userName);
			if(nameArray!=null){
				
				table.put("firstName", nameArray[0]);
				table.put("middleName", (nameArray[1]==null)?"":nameArray[1]);
				table.put("lastName", (nameArray[2]==null)?"":nameArray[2]);
				table.put("userType", nameArray[3]);
			}
			table.put("roleName", userDao.getUserRoleName(userName));
			table.put("defaultLocation", EviService.getDefaultLocation());
			table.put("proPic", EviService.getProPic(userName));
			table.put("ip", EviService.getServerIp());
			return table;	
		}
		else
			return null;
	}

	@Override
	public boolean validateEmail(String userName, String emailId) {
		
		return emailId.equals(userDao.getPrimaryEmailId(userName));
	}

	@Override
	public Properties getRandomQuestions(String userName) {
		
		List<Object[]> list=qnDao.getSecurityQns(userName);
		Properties qns=new Properties();
		
		int listSize=list.size();
		if(listSize!=0){

			int firstQnIndex=1,secondQnIndex=2;
			firstQnIndex=new Random().nextInt(3);
			do
				secondQnIndex = new Random().nextInt(2);
			
			while (secondQnIndex == firstQnIndex);
			
			qns.put("firstQuestion",(String)((list.get(0))[firstQnIndex]));
			qns.put("secondQuestion",(String)((list.get(0))[secondQnIndex]));
		}
		return qns;
	}

	@Override
	public boolean verifySecurityQuestionAndAnswer(String userName,
			String securityQuestion1, String securityAnswer1,
			String securityQuestion2, String securityAnswer2) {
		
		SecurityQuestion qnObj=qnDao.getSecurityQuestionObj(userName);
		if(qnObj==null)
			return false;
		
		boolean flag1=false;
		boolean flag2=false;
		
		/////////////////////////////
		if(qnObj.getSecurityQuestion1().equals(securityQuestion1)
				&& qnObj.getSecurityAnswer1().equals(securityAnswer1)){
			
			flag1=true;
		}
		else if(qnObj.getSecurityQuestion2().equals(securityQuestion1)
				&& qnObj.getSecurityAnswer2().equals(securityAnswer1)){
			
			flag1=true;
		}
		else if(qnObj.getSecurityQuestion3().equals(securityQuestion1)
				&& qnObj.getSecurityAnswer3().equals(securityAnswer1)){
			
			flag1=true;
		}
		///////////////////////////////
		if(qnObj.getSecurityQuestion1().equals(securityQuestion2)
				&& qnObj.getSecurityAnswer1().equals(securityAnswer2)){
			
			flag2=true;
		}
		else if(qnObj.getSecurityQuestion2().equals(securityQuestion2)
				&& qnObj.getSecurityAnswer2().equals(securityAnswer2)){
			
			flag2=true;
		}
		else if(qnObj.getSecurityQuestion3().equals(securityQuestion2)
				&& qnObj.getSecurityAnswer3().equals(securityAnswer2)){
			
			flag2=true;
		}
		
		return flag1&&flag2;
	}

	@Override
	public void sendToken(String userName, int token, byte smsOrEmail)
			throws AddressException, IOException, MessagingException {
		
		if(smsOrEmail==1){//Send mail
			
			String to=userDao.getPrimaryEmailId(userName);
			String subject			 = "Password Reset";
			String body   			 = "Verification Code:- " + token;
			
			EmailSms.sendMail(to, subject, body);
		}
		else if(smsOrEmail==0){//Send sms
			
		}
	}
	
	@Override
	public boolean isSamePassword(String userName, String password) {
		
		return password.equals(userDao.getPassword(userName));
	}

	@Override
	public long checkPrivateNotePrivilege(String userName) {
		return privilegeDao.checkPrivateNotePrivilege(userName);
	}

}
