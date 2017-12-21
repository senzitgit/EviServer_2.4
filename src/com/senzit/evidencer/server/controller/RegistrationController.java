package com.senzit.evidencer.server.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.senzit.evidencer.server.service.RegService;
import com.senzit.evidencer.server.subservice.IdentityCode;
import com.senzit.evidencer.server.subservice.JsonParser;

@RestController
public class RegistrationController {
	
	static Logger log = Logger.getLogger(RegistrationController.class.getName());
	
	@Autowired 
	private RegService regService;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String regInit(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("userName") String userName,
			@RequestParam("firstName") String firstName,
			@RequestParam("password") String password,
			@RequestParam("eMail") String primaryEmail,
			@RequestParam("mobileNo") String primaryMobileNo){
		
		
		Byte resultCode=0;
		String message="";
		Hashtable<String,Object> result=new Hashtable<String,Object>();
		
		///////////////////////////
		log.debug("register");
		log.debug("UserName:"+userName);
		log.debug(firstName);
		log.debug(password);
		log.debug("Email:"+primaryEmail);
		log.debug(primaryMobileNo);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////////
		
		//if(regService.isValidDomain(primaryEmail)){
			
			try {
				if(regService.isValidUserParams(userName, primaryEmail)==0){
					
					int smsCode=IdentityCode.generateRandomCode();
					int emailCode=IdentityCode.generateRandomCode();
					////////////
					log.debug("SmsCode:"+smsCode);
					log.debug("EmailCode:"+emailCode);
					///////////
					
					regService.sendNotification(firstName, primaryEmail, primaryMobileNo, emailCode, smsCode);
					
					sessionObj.setAttribute("regUserName", userName);
					sessionObj.setAttribute("regFirstName", firstName);
					sessionObj.setAttribute("regPassword", password);
					sessionObj.setAttribute("regEmail", primaryEmail);
					sessionObj.setAttribute("regMobile", primaryMobileNo);
					sessionObj.setAttribute("regEmailCode", emailCode);
					sessionObj.setAttribute("regSmsCode", smsCode);
						
					result.put("defaultLocation", regService.getDefaultLocation());
					message="Success";
					resultCode=1;
				}
				else
					message="An account with similar username or emailid exists.";
			} catch (Exception e) {
				
				e.printStackTrace();
				message = "Unknown error! Verification code couldn't be sent. Please try again.";
				//message="Server Exception: "+e.getCause();
				//message="Server Exception: "+e.getMessage();
				resultCode=2;
			}
		//}
		//else
		//	message="Invalid mail domain";	
		
		JsonParser<String, Hashtable<String,Object>> jsonResponse = new JsonParser<String, Hashtable<String,Object>>("register",resultCode,message,"UserRegResult",result);
		return jsonResponse.JsonResponseText();
	}	
	
	@RequestMapping(value = "/regCode", method = RequestMethod.POST)
	public String regInit(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("smsCode") Integer smsCode,
			@RequestParam("emailCode") Integer emailCode){
		
		
		Byte resultCode=0;
		String message="";
		String result="";
		
		String userName=(String)sessionObj.getAttribute("regUserName");
		///////////////////////////
		log.debug("regCode");
		log.debug("UserName:"+userName);
		log.debug("SmsCode:"+smsCode);
		log.debug("EmailCode:"+emailCode);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////////
		
		if(userName==null){
			message="Your session has been expired!";
		}
		else if(sessionObj.getAttribute("regEmailCode").equals(emailCode)
				&& sessionObj.getAttribute("regSmsCode").equals(smsCode)){
			
			resultCode=1;
			message="Success";
		}
		else
			message="Provided code doesn't match! Please try again.";		
		
		JsonParser<String, String> jsonResponse = new JsonParser<String, String>("regCode",resultCode,message,"UserRegResult",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/regPro", method = RequestMethod.POST)
	public String profileCreation(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("firstName") String firstName,
			@RequestParam("middleName") String middleName,
			@RequestParam("lastName") String lastName,
			@RequestParam("secMail") String secMail,
			@RequestParam("secMobile") String secMobile,
			@RequestParam("questionOne") String qn1,
			@RequestParam("answerOne") String ans1,
			@RequestParam("questionTwo") String qn2,
			@RequestParam("answerTwo") String ans2,
			@RequestParam("questionThree") String qn3,
			@RequestParam("answerThree") String ans3){
		
		
		Byte resultCode=0;
		String message="";
		String result="";
		
		String userName=(String)sessionObj.getAttribute("regUserName");
		///////////////////////////
		log.debug("regPro");
		log.debug("Username"+userName);
		log.debug(middleName);
		log.debug(lastName);
		log.debug(secMail);
		log.debug(secMobile);
		log.debug(qn1);
		log.debug(ans1);
		log.debug(qn2);
		log.debug(ans2);
		log.debug(qn3);
		log.debug(ans3);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////////
		
		if(userName==null){
			message="Your session has been expired!";
		}
		else{		
			
			try {
				regService.notifyAdmin(userName);
				regService.saveUserProfile(userName, (String)sessionObj.getAttribute("regPassword"),
						firstName, middleName, lastName,
						(String)sessionObj.getAttribute("regEmail"), (String)sessionObj.getAttribute("regMobile"),
						secMail, secMobile, qn1, ans1, qn2, ans2, qn3, ans3);
				
				sessionObj.invalidate();
				resultCode=1;
				message="Success";
				
			} catch ( IOException | MessagingException  e) {
				
				e.printStackTrace();
				message = "Unknown error!";
				//message="Server Exception: "+e.getCause();
				//message="Server Exception: "+e.getMessage();
				resultCode=2;
			}catch (HibernateException e) {
				
				e.printStackTrace();
				message = "Couldn't establish connection with database. Check server configuration!";
				//message="Server Exception: "+e.getCause();
				//message="Server Exception: "+e.getMessage();
				resultCode=2;
			}
		}
		
		JsonParser<String, String> jsonResponse = new JsonParser<String, String>("regPro",resultCode,message,"UserRegResult",result);
		return jsonResponse.JsonResponseText();
	}
	
//	@RequestMapping(value = "/getRegistrations", method = RequestMethod.POST)
//	public String getRegistrations(HttpSession sessionObj){
//		
//		Byte resultCode=0;
//		String message="";
//		List<Hashtable<String, Object>> result=new ArrayList<Hashtable<String, Object>>();
//		
//		String userName=(String)sessionObj.getAttribute("userName");
//		///////////////////////////
//		log.debug("getRegistrations");
//		log.debug("Username:"+userName);
//		//////////////////////////
//		if(userName==null && !(boolean)sessionObj.getAttribute("admin"))
//			message="Invalid session";
//		else{
//			
//			try {
//				List<Hashtable<String, Object>> temp=regService.getPartialRegisters();
//				if(temp==null)
//					message="Server error";
//				else{
//					
//					result=temp;
//					resultCode=1;
//					message="Success";
//				}
//			} catch (Exception e) {
//				
//				e.printStackTrace();
//				message="Server Exception: "+e.getCause();
//				//message="Server Exception: "+e.getMessage();
//				resultCode=2;
//			}
//		}
//		
//		JsonParser<String, List<Hashtable<String, Object>>> jsonResponse = new JsonParser<String, List<Hashtable<String, Object>>>("getRegistrations",resultCode,message,"UserRegResult",result);
//		return jsonResponse.JsonResponseText();
//	}
	
	@RequestMapping(value = "/confirmReg", method = RequestMethod.POST)
	public String confirmReg(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("userName") String reqUserName,
			@RequestParam("userType") Byte userType,
			@RequestParam("roleId") Integer roleId,
			@RequestParam("privilegeIdJson") String privilegeIdJson,
			@RequestParam("acceptOrReject") boolean acceptOrReject){
		
		Byte resultCode=0;
		String message="";
		String result="";
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////////
		log.debug("confirmReg");
		log.debug("Username:"+userName);
		log.debug("reqUserName:"+reqUserName);
		log.debug("userType:"+userType);
		log.debug("roleId:"+roleId);
		log.debug("privilegeIdJson:"+privilegeIdJson);
		log.debug("acceptOrReject:"+acceptOrReject);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////////
		if(userName==null && !(boolean)sessionObj.getAttribute("admin"))
			message="Invalid session";
		else {
			
			ArrayList<Integer> privilegeIdList=new ArrayList<Integer>();
			try {
				JSONObject jsonData = new JSONObject(privilegeIdJson);
				JSONArray array=jsonData.getJSONArray("privilageList");
				int length=array.length();
				for(int i=0;i<length;i++){
					
					privilegeIdList.add(Integer.parseInt(array.get(i).toString()));
				}
				regService.confirmRegistration(reqUserName, userType, roleId,privilegeIdList, acceptOrReject);
				resultCode=1;
				message="Success";
			} catch (Exception e) {
				
				e.printStackTrace();
				message="Server Exception: "+e.getCause();
				//message="Server Exception: "+e.getMessage();
				resultCode=2;
			}
		}
		
		JsonParser<String, String> jsonResponse = new JsonParser<String, String>("confirmReg",resultCode,message,"UserRegResult",result);
		return jsonResponse.JsonResponseText();
	}
	
}
