package com.senzit.evidencer.server.controller;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.senzit.evidencer.server.service.MobService;
import com.senzit.evidencer.server.subservice.EmailSms;
import com.senzit.evidencer.server.subservice.IdentityCode;
import com.senzit.evidencer.server.subservice.JsonParser;

@RestController
public class MobController {
	
	static Logger log = Logger.getLogger(RegistrationController.class.getName());
	@Autowired 
	private MobService mobService;
	
	@RequestMapping(value = "/registerMob", method = RequestMethod.POST)
	public String regInit(HttpSession sessionObj,
			@RequestParam("email") String email,
			@RequestParam("password") String password){
		
		Byte resultCode=0;
		String message="";
		String result="";
		
		log.debug("registerMob");
		log.debug("email:"+email);
		log.debug(password);
		
		try {
			if(mobService.checkUserParams(email)==0){
				
				int emailCode=IdentityCode.generateRandomCode();// CODE MUST BE SENT VIA SMS
				///////////////////////
				log.debug("emailCode:"+emailCode);
				//////////////////////
				EmailSms.sendMail(email, "EviMobRegCode", ((Integer)emailCode).toString());
				sessionObj.setAttribute("mobRegEmail", email);
				sessionObj.setAttribute("mobPassword", password);
				sessionObj.setAttribute("mobRegCode", emailCode);
				
				message = "Success";
				resultCode = 1;
			}
			else
				message = "Emailid exists";
		} catch (Exception e) {
			
			resultCode = 2;
			message = "Server Exception";
			e.printStackTrace();
		}
		
		JsonParser<String,String> jsonResponse = new JsonParser<String, String>("registerMob",resultCode,message,"MobRegResult",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/regCodeMob", method = RequestMethod.POST)
	public String regCode(HttpSession sessionObj,
			@RequestParam("emailCode") String mobRegCode){
		
		Byte resultCode=0;
		String message="";
		String result="";
		
		String email=(String)sessionObj.getAttribute("mobRegEmail");
		
		log.debug("email:"+email);
		log.debug("registerMob");
		log.debug("emailCode:"+mobRegCode);
		
		if(email==null){
			message="Invalid session";
		}
		else if((sessionObj.getAttribute("mobRegCode").toString()).equals(mobRegCode)){
			
			try {
				if(mobService.register(email,sessionObj.getAttribute("mobPassword").toString()) != null){
					
					message = "Success";
					resultCode = 1;
					sessionObj.removeAttribute("mobRegEmail");
					sessionObj.removeAttribute("mobPassword");
					sessionObj.removeAttribute("mobRegCode");
					
				}
				else
					message = "Server error";
			} catch (Exception e) {
				
				e.printStackTrace();
				resultCode = 2;
				message = "Server Exception";
			}
		}
		else
			message = "Incorrect code";
		
		JsonParser<String,String> jsonResponse = new JsonParser<String, String>("regCodeMob",resultCode,message,"MobRegResult",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/uploadMobFile", method = RequestMethod.POST)
	public String newMobFile(HttpSession sessionObj,
			@RequestParam("file") MultipartFile[] fileArray,
			@RequestParam("detail") String detail,
			@RequestParam("description") String description,
			@RequestParam("latitude") String latitude,
			@RequestParam("longitude") String longitude,
			@RequestParam("createdByFlag") Boolean flag,
			@RequestParam("userName") String userName){
		
		Byte resultCode=0;
		String message="";
		String result="";
		
		log.debug("UserName:"+userName);
		log.debug("uploadMobFile");
		log.debug("createdByFlag:"+flag);
		log.debug("detail:"+detail);
		log.debug("description:"+description);
		log.debug("location:"+latitude+"::"+longitude);
		log.debug("FileSize:"+fileArray.length);
		for(int i=0;i<fileArray.length;i++)
			System.out.println(fileArray[i].getSize());
		
		if(userName==null){
			message="Invalid session";
		}
		else if(fileArray.length==0)
			message = "File upload error";
		else{
			
			try {
				result = mobService.insertNewFile(fileArray,detail, description,latitude,longitude, userName,flag);
				message = "Success";
				resultCode = 1;
			} catch (Exception e) {
				
				e.printStackTrace();
				message = "Server Exception";
				resultCode = 2;
			}
		}
		
		JsonParser<String,String> jsonResponse = new JsonParser<String, String>("uploadMobFile",resultCode,message,"randomCode",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/getMobFile", method = RequestMethod.POST)
	public String getMobFile(HttpSession sessionObj,
			@RequestParam("randomCode") String randomCode){
		
		Byte resultCode=0;
		String message="";
		Hashtable<String, Object> result= new Hashtable<String, Object>();
		
		String userName=(String)sessionObj.getAttribute("userName");

		log.debug("getMobFile");
		log.debug("UserName:"+userName);
		
		if(userName==null){
			message="Invalid session";
		}
		else{
			
			try {
				Hashtable<String, Object> temp = mobService.getMobFileLink(randomCode);
				if(temp==null)					
					message = "No records found. Please try again";
				else if(temp.isEmpty())
					message="File not found";
				else{
					
					result=temp;
					message = "Success";
					resultCode = 1;
					
				}
			} catch (Exception e) {
				
				e.printStackTrace();
				message = "Server Exception";
				resultCode = 2;
			}
		}
		
		JsonParser<String,Hashtable<String, Object>> jsonResponse = new JsonParser<String, Hashtable<String, Object>>("getMobFile",resultCode,message,"fileLink",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/getAllMobFiles", method = RequestMethod.POST)
	public String getMobFile(HttpSession sessionObj){
		
		Byte resultCode=0;
		String message="";
		List<Hashtable<String, Object>> result= new ArrayList<Hashtable<String, Object>>();
		
		String userName=(String)sessionObj.getAttribute("userName");

		log.debug("getAllMobFile");
		log.debug("UserName:"+userName);
		
		if(userName==null){
			message="Invalid session";
		}
		else{
			
			try {
				List<Hashtable<String, Object>> temp = mobService.getMobFileLink();
				if(temp==null){
					message = "No records found.";
					resultCode=1; //As per portal
				}
				else{
					result=temp;
					resultCode = 1;
					message =  "Success";
				}
			} catch (Exception e) {
				
				e.printStackTrace();
				message = "Server Exception";
				resultCode = 2;
			}
		}
		
		JsonParser<String,List<Hashtable<String, Object>>> jsonResponse = new JsonParser<String, List<Hashtable<String, Object>>>("getAllMobFile",resultCode,message,"fileList",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/getAllFileStatus", method = RequestMethod.POST)
	public String getAllFileStatus(HttpSession sessionObj){
		
		Byte resultCode=0;
		String message="";
		List<Hashtable<String, String>> result= new ArrayList<Hashtable<String, String>>();
		
		String userName=(String)sessionObj.getAttribute("mobUserName");

		log.debug("getAllFileStatus");
		log.debug("UserName:"+userName);
		
		if(userName==null){
			message="Invalid session";
		}
		else{
			
			try {
				result = mobService.getMobFileStatus(userName);
				resultCode = 1;
				message =  "Success";
			} catch (Exception e) {
				
				e.printStackTrace();
				message = "Server Exception";
				resultCode = 2;
			}
		}
		
		JsonParser<String,List<Hashtable<String, String>>> jsonResponse = new JsonParser<String, List<Hashtable<String, String>>>("getAllFileStatus",resultCode,message,"fileList",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/setFileStatus", method = RequestMethod.POST)
	public String setFileStatus(HttpSession sessionObj,@RequestParam("randomCode") String randomCode,
			@RequestParam("status") String status){
		
		Byte resultCode=0;
		String message="";
		List<Hashtable<String, String>> result= new ArrayList<Hashtable<String, String>>();
		
		String userName=(String)sessionObj.getAttribute("userName");

		log.debug("setFileStatus");
		log.debug("UserName:"+userName);
		log.debug("randomCode:"+randomCode);
		log.debug("status:"+status);
		
		if(userName==null){
			message="Invalid session";
		}
		else{
			
			try {
				if(mobService.setMobFileStatus(randomCode, status)>0){
					
					resultCode = 1;
					message =  "Success";
				}
				else
					message = "Update failed";
			} catch (Exception e) {
				
				e.printStackTrace();
				message = "Server Exception";
				resultCode = 2;
			}
		}
		
		JsonParser<String,List<Hashtable<String, String>>> jsonResponse = new JsonParser<String, List<Hashtable<String, String>>>("setFileStatus",resultCode,message,"fileList",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/mobLogin", method = RequestMethod.POST)
	public String mobLogin(HttpSession sessionObj,
			@RequestParam("emailId") String emailId,
			@RequestParam("password") String password){
		
		Byte resultCode=0;
		String message="";
		List<Hashtable<String, String>> result= new ArrayList<Hashtable<String, String>>();

		log.debug("mobLogin");
		log.debug("emailId:"+emailId);
		log.debug("password:"+password);
		
		try {
			if(mobService.login(emailId, password)>0){				

				sessionObj.setAttribute("mobUserName", emailId);
				resultCode = 1;
				message =  "Success";
			}
			else message = "Invalid emailid or password";
		} catch (Exception e) {
			
			e.printStackTrace();
			message = "Server Exception";
			resultCode = 2;
		}

		JsonParser<String,List<Hashtable<String, String>>> jsonResponse = new JsonParser<String, List<Hashtable<String, String>>>("mobLogin",resultCode,message,"loginResult",result);
		return jsonResponse.JsonResponseText();
	
	}
	
	@RequestMapping(value = "/mobLogout", method = RequestMethod.POST)
	public String mobLogout(HttpSession sessionObj){
		
		Byte resultCode=0;
		String message="";
		String result= "";
		String userName=(String)sessionObj.getAttribute("mobUserName");

		log.debug("mobLogout");
		log.debug("userName:"+userName);
		
		sessionObj.removeAttribute("mobUserName");
		sessionObj.invalidate();
		message = "Success";
		resultCode = 1;
		
		JsonParser<String,String> jsonResponse = new JsonParser<String,String>("mobLogout",resultCode,message,"loginResult",result);
		return jsonResponse.JsonResponseText();
	
	}
	
	@RequestMapping(value = "/mobForgotPassword1", method = RequestMethod.POST)
	public String mobForgotPassword1(HttpSession sessionObj,@RequestParam("email") String email){
		
		Byte resultCode=0;
		String message="";
		String result= "";

		log.debug("mobForgotPassword1");
		log.debug("userName:"+email);
		
		try {
			if(mobService.checkUserParams(email)>0){
				
				String randomCode = ((Long)(Math.round(Math.random()* 89999) + 10000)).toString();
				sessionObj.setAttribute("forgotMobCode", randomCode);
				sessionObj.setAttribute("forgotMobUser", email);
				log.debug("Forgot Password Code "+randomCode);
				EmailSms.sendMail(email, "EviMobForgotPasswordCode", randomCode);
				resultCode = 1;
				message = "Success";
			}
			else
				message = "Invalid emailid";
		} catch (Exception e) {
			e.printStackTrace();
			message = "Server Exception";
			resultCode = 2;
		}
		
		JsonParser<String,String> jsonResponse = new JsonParser<String,String>("mobForgotPassword1",resultCode,message,"result",result);
		return jsonResponse.JsonResponseText();
	
	}
	
	@RequestMapping(value = "/mobForgotPassword2", method = RequestMethod.POST)
	public String mobForgotPassword2(HttpSession sessionObj,@RequestParam("randomCode") String randomCode,
			@RequestParam("newPassword") String newPassword){
		
		Byte resultCode=0;
		String message="";
		String result= "";

		log.debug("mobForgotPassword2");
		String email = (String) sessionObj.getAttribute("forgotMobUser");
		String randomCodeSession = (String) sessionObj.getAttribute("forgotMobCode");
		log.debug("userName:"+email);
		log.debug("randomCode:"+randomCode);
		log.debug("randomCodeSession:"+randomCodeSession);
		log.debug(newPassword);
		
		if(randomCodeSession == null || randomCode == null)
			message = "Invalid session";
		else if( ! randomCodeSession.equals(randomCode))
			message = "Wrong code";
		else{
			
			try {
				mobService.upadtePassword(email, newPassword);
				message = "Success";
				resultCode=1;
			} catch (Exception e) {
				e.printStackTrace();
				message = "Server Exception";
				resultCode = 2;
			}
		}
		
		JsonParser<String,String> jsonResponse = new JsonParser<String,String>("mobForgotPassword2",resultCode,message,"result",result);
		return jsonResponse.JsonResponseText();
	
	}

}
