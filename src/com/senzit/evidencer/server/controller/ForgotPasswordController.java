package com.senzit.evidencer.server.controller;

import java.util.Properties;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.senzit.evidencer.server.service.AuthService;
import com.senzit.evidencer.server.service.UserService;
import com.senzit.evidencer.server.subservice.IdentityCode;
import com.senzit.evidencer.server.subservice.JsonParser;

@RestController
public class ForgotPasswordController {
	
	static Logger log = Logger.getLogger(ForgotPasswordController.class.getName());

	@Autowired 
	private AuthService authService;
	@Autowired 
	private UserService userService;
	
	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
	public String forgotPassword(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("userName") String userName,@RequestParam("eMail") String emailId){
		
		Byte resultCode=0;
		String message="";
		Properties result=new Properties();
		
		///////////////////////
		log.debug("forgotPassword");
		log.debug("Username:"+userName);
		log.debug("Emailid:"+emailId);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////
		try {
			if(authService.validateEmail(userName, emailId)){
				
				result=authService.getRandomQuestions(userName);
				if(!result.isEmpty()){
					
					sessionObj.setAttribute("forgotUserName", userName);
					resultCode=1;
					message="Success";					
				}
				else
					message="Your account has not been approved yet.";
			}
			else
				message="Username and emailid doesn't match! Please try again.";
		} catch (Exception e) {
			
			e.printStackTrace();
			message = "Couldn't establish connection with database. Check server configuration!";
			//message="Server Exception: "+e.getCause();
			resultCode=2;
		}
		
		JsonParser<String, Properties> jsonResponse = new JsonParser<String, Properties>("forgotPassword",resultCode,message,"ForgotPasswordResult",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/forgotPassAnswer", method = RequestMethod.POST)
	public String forgotPasswordAnswer(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("securityQuestion1") String qn1,
			@RequestParam("securityAnswer1") String ans1,
			@RequestParam("securityQuestion2") String qn2,
			@RequestParam("securityAnswer2") String ans2){
		
		Byte resultCode=0;
		String message="";
		String result="";
		
		String userName=(String)sessionObj.getAttribute("forgotUserName");
		byte smsOrEmail=1;		// FOR FURURE USE IF NEEDED
		///////////////////////
		log.debug("forgotPassAnswer");
		log.debug("Username:"+userName);
		log.debug(qn1);
		log.debug(ans1);
		log.debug(qn2);
		log.debug(ans2);
		log.debug("Sms/Email:"+smsOrEmail);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////
		if(userName==null)
			message="Your session has been expired!";
		else
			try {
				
				if(authService.verifySecurityQuestionAndAnswer(userName, qn1, ans1, qn2, ans2)){
					
					int token=IdentityCode.generateRandomCode();
					////////////////
					log.debug(token);
					///////////////
					authService.sendToken(userName, token, smsOrEmail);					
					sessionObj.setAttribute("token", token);
					resultCode=1;
					message="Success";
					
				}
				else
					message="Security answers don't match! Please try again.";
			} catch (Exception e) {
				
				e.printStackTrace();
				message = "Couldn't establish connection with database. Check server configuration!";
				//message="Server Exception: "+e.getCause();
				resultCode=2;
			}
		
		JsonParser<String, String> jsonResponse = new JsonParser<String, String>("forgotPassAnswer",resultCode,message,"ForgotPasswordResult",result);
		return jsonResponse.JsonResponseText();		
	}
	
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public String resetPassword(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("token") int token,
			@RequestParam("newPassword") String newPassword){
		
		Byte resultCode=0;
		String message="";
		String result="";
		
		String userName=(String)sessionObj.getAttribute("forgotUserName");
		Integer validToken=(Integer)sessionObj.getAttribute("token");
		///////////////////////
		log.debug("resetPassword");
		log.debug("Username:"+userName);
		log.debug("Token:"+token);
		log.debug("Validtoken"+validToken);
		log.debug(newPassword);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////
		if(userName==null || validToken==null)
			message="Your session has been expired!";
		else if(token==validToken){
			
			try {
				if(authService.isSamePassword(userName, newPassword))
					message="Password already used! Please try a different one";
				else if(userService.updatePassword(userName, newPassword)>0){
					
					resultCode=1;
					message="Success";
					sessionObj.invalidate();
				}
				else
					message="Unknown error! Password not updated.";
			} catch (Exception e) {
				
				e.printStackTrace();
				message = "Couldn't establish connection with database. Check server configuration!";
				//message="Server Exception: "+e.getCause();
				resultCode=2;
			}
		}
		else
			message="Provided code doesn't match! Please try again.";
		
		JsonParser<String, String> jsonResponse = new JsonParser<String, String>("resetPassword",resultCode,message,"ForgotPasswordResult",result);
		return jsonResponse.JsonResponseText();
	}

}
