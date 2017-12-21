package com.senzit.evidencer.server.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.senzit.evidencer.server.dao.PrivilegeDao;
import com.senzit.evidencer.server.dao.SecurityQuestionDao;
import com.senzit.evidencer.server.dao.UserDao;
import com.senzit.evidencer.server.model.EviProperty;
import com.senzit.evidencer.server.model.Privilege;
import com.senzit.evidencer.server.model.SecurityQuestion;
import com.senzit.evidencer.server.model.User;
import com.senzit.evidencer.server.model.UserPrivilege;
import com.senzit.evidencer.server.subservice.EmailSms;

public class RegServiceImp implements RegService {
	
	private UserDao userDao;
	private SecurityQuestionDao qnDao;
	private PrivilegeDao privilegeDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setQnDao(SecurityQuestionDao qnDao) {
		this.qnDao = qnDao;
	}

	public void setPrivilegeDao(PrivilegeDao privilegeDao) {
		this.privilegeDao = privilegeDao;
	}

	@Override
	public boolean isValidDomain(String emailId) {
		
		String validDomain=EviProperty.evidencerProperty.getProperty("validDomainName");
		String domain=emailId.substring(emailId.indexOf('@')+1);
		System.out.println(domain);
		System.out.println(domain.equals(validDomain) || domain.equals("gmail.com"));
		String gmail = "gmail.com";
		String dotNet = "senzit.net";
		return (domain.equals(validDomain) || domain.equals(gmail) || domain.equals(dotNet));
	}

	@Override
	public long isValidUserParams(String userName, String primaryEmailId) {
		
		return userDao.checkUserNameEmail(userName, primaryEmailId);
	}

	@Override
	public String getDefaultLocation() {
		
		return EviProperty.evidencerProperty.getProperty("defaultLocationName");
	}

	@Override
	public void sendNotification(String firstName, String primaryEmailId,
			String primaryMobileNumber, int emailCode, int smsCode) throws AddressException, IOException, MessagingException {
				
		String to     		 	 = primaryEmailId;
		String subject			 = "Evidencer Verification Code";
		String body   			 = "Verification Code:- " + emailCode;
		
		EmailSms.sendMail(to, subject, body);
	}
	
	@Override
	public boolean notifyAdmin(String userName) throws AddressException, IOException, MessagingException {
		
		Object[] userDetails=userDao.getUserPrimaryDetails(userName);
		if(userDetails==null)
			return false;
		String firstName=userDetails[0].toString();
		String mailId=userDetails[1].toString();

		String to=userDao.getAdminEmail();
		String subject = "Request For New Registration:";
		String body    = "Username : "+userName+"\nFirstName : "+firstName+"\nEmailid : "+mailId;
		
		EmailSms.sendMail(to, subject, body);		
		return true;
	}

	@Override
	public void saveUserProfile(String userName, String password, String firstName,
			String middleName, String lastName,String primaryEmailId, String primaryMobileNo,
			String secondaryEmailId, String secondaryMobileNumber, String securityQn1,
			String securityAns1, String securityQn2, String securityAns2,
			String securityQn3, String securityAns3) {
		
		User user= new User();
		user.setUserName(userName);
		user.setPassword(password);
		user.setFirstName(firstName);
		user.setMiddleName(middleName);
		user.setLastName(lastName);
		user.setPrimaryEmailId(primaryEmailId);
		user.setPrimaryMobileNumber(primaryMobileNo);
		user.setSecondaryEmailId(secondaryEmailId);
		user.setSecondaryMobileNumber(secondaryMobileNumber);
		
		userDao.insertNewUser(user);
		
		SecurityQuestion securityQnObj=new SecurityQuestion();
		securityQnObj.setSecurityQuestion1(securityQn1);
		securityQnObj.setSecurityAnswer1(securityAns1);
		securityQnObj.setSecurityQuestion2(securityQn2);
		securityQnObj.setSecurityAnswer2(securityAns2);
		securityQnObj.setSecurityQuestion3(securityQn3);
		securityQnObj.setSecurityAnswer3(securityAns3);
		securityQnObj.setUser(user);
		
		qnDao.insertNew(securityQnObj);			
		
	}

	@Override
	public List<Hashtable<String, Object>> getPartialRegisters() {
		
		List<Object[]> list=userDao.getPartialRegisters();
		if(list==null)
			return null;
		List<Hashtable<String, Object>> result=new ArrayList<Hashtable<String, Object>>();
		for(Object[] obj:list){
			
			Hashtable<String,Object> temp=new Hashtable<String,Object>();
			temp.put("userName", obj[0].toString());			
			temp.put("firstName", obj[1].toString());
			temp.put("middleName", obj[2]==null?"":obj[2].toString());
			temp.put("lastName", obj[3]==null?"":obj[3].toString());
			temp.put("primaryEmailId", obj[4].toString());
			temp.put("primaryMobileNumber", obj[5].toString());
			
			result.add(temp);
		}
		return result;
	}

	@Override
	public void confirmRegistration(String userName, Byte type,
			Integer roleId, List<Integer> privilegeIdList, boolean acceptOrReject) {
		
		if(acceptOrReject){
			
			boolean flag=(userDao.setRoleType(userName, type, roleId)>0)?true:false;
			Privilege prObj=new Privilege();
			User userObj=new User();
			userObj.setUserName(userName);
			int length=privilegeIdList.size();
			for(int i=0;i<length;i++){			
				
				UserPrivilege upObj=new UserPrivilege();
				upObj.setUser(userObj);
				prObj.setPrivilegeId(privilegeIdList.get(i));
				upObj.setPrivilege(prObj);
				/////////////////////////////////////////////////////////////////////////////
				/////  NEED TO CHANGE  ////
				Calendar cal = Calendar.getInstance();
				Date today = cal.getTime();
				cal.add(Calendar.YEAR, 1);
				Date nextYear = cal.getTime();
				upObj.setDateOfPurchase(today);
				upObj.setDateOfExpire(nextYear);
				/////////////////////////////////
				////////////////////////////////////////////////////////////////////////////////
				
				flag=flag && (privilegeDao.insertUserPrivilege(upObj)==null)?false:true;
			}
		}
		else{
			qnDao.deleteQn(userName);
			userDao.removeUser(userName);
		}
	}

}
