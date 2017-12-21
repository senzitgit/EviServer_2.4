package com.senzit.evidencer.server.controller;

import java.util.HashMap;

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
import com.senzit.evidencer.server.subservice.EviService;
import com.senzit.evidencer.server.subservice.JsonParser;

@RestController
public class UserController {
	
	static Logger log = Logger.getLogger(UserController.class.getName());
	
	@Autowired 
	private UserService userService;
	@Autowired 
	private AuthService authService;
	
	@RequestMapping(value = "/changePassword1", method = RequestMethod.POST)
	public String authenticate(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("userName") String userNameClient,
			@RequestParam("password") String password){
		
		Byte resultCode=0;
		String message="";
		String result="";
		
		String userNameSession=(String)sessionObj.getAttribute("userName");
		///////////////////////
		log.debug("changePassword1");
		log.debug("UsernameClient:"+userNameClient);
		log.debug("Username:"+userNameSession);
		log.debug(password);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////
		try {
			if(userNameSession==null)
				message="Your session has been expired! Please login.";
			else if(!userNameSession.equals(userNameClient))
				message="Unauthorized access! Please use the same credentials for login.";
			else if(!authService.authenticate(userNameClient, password))
				message="Password is incorrect! Please try again.";
			else{
				sessionObj.setAttribute("changePassUser", userNameClient);
				resultCode=1;
				message="Success";
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			message = "Couldn't establish connection with database. Check server configuration!";
			//message="Server Exception: "+e.getCause();
			//message="Server Exception: "+e.getMessage();
			resultCode=2;
		}
		JsonParser<String, String> jsonResponse = new JsonParser<String, String>("changePassword1",resultCode,message,"ChangePasswordResult",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/changePassword2", method = RequestMethod.POST)
	public String changePassword(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("newPassword") String newPassword){
		
		Byte resultCode=0;
		String message="";
		String result="";
		
		String userName=(String)sessionObj.getAttribute("userName");
		String userNameClient=(String)sessionObj.getAttribute("changePassUser");
		///////////////////////
		log.debug("changePassword2");
		log.debug("Username:"+userName);
		log.debug(newPassword);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////
		try {
			if(userName==null || !userName.equals(userNameClient))
				message="Your session has been expired! Please login.";
			else if(authService.isSamePassword(userName, newPassword))
				message="Password already used! Please try a different one";
			else if(userService.updatePassword(userName, newPassword)>0){
				
				sessionObj.removeAttribute("changePassUser");
				resultCode=1;
				message="Success";
			}
			else
				message="Unknown error! Password not updated.";
		} catch (Exception e) {
			
			e.printStackTrace();
			message = "Couldn't establish connection with database. Check server configuration!";
			//message="Server Exception: "+e.getCause();
			//message="Server Exception: "+e.getMessage();
			resultCode=2;
		}
		
		JsonParser<String, String> jsonResponse = new JsonParser<String, String>("changePassword2",resultCode,message,"ChangePasswordResult",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/changePasswordPortal", method = RequestMethod.POST)
	public String changePasswordPortal(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("newPassword") String newPassword){
		
		Byte resultCode=0;
		String message="";
		String result="";
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////
		log.debug("changePasswordPortal");
		log.debug("Username:"+userName);
		log.debug(newPassword);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////
		try {
			if(userName==null)
				message="Invalid session";
			if(authService.isSamePassword(userName, newPassword))
				message="Password already used! Please try a different one";
			else if(userService.updatePassword(userName, newPassword)>0){
				
				resultCode=1;
				message="Success";
			}
			else
				message="Password not updated";
		} catch (Exception e) {
			
			e.printStackTrace();
			message="Server Exception: "+e.getCause();
			//message="Server Exception: "+e.getMessage();
			resultCode=2;
		}
		
		JsonParser<String, String> jsonResponse = new JsonParser<String, String>("changePasswordPortal",resultCode,message,"ChangePasswordResult",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/viewProfile", method = RequestMethod.POST)
	public String viewProfile(HttpSession sessionObj,HttpServletResponse response){
		
		Byte resultCode=0;
		String message="";
		HashMap<String,String> result=new HashMap<String,String>();
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////
		log.debug("viewProfile");
		log.debug("Username:"+userName);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////
		try {
			if(userName==null)
				message="Your session has been expired! Please login.";
			else if( (result=userService.getUserProfile(userName)).isEmpty() )
				message="Server error";
			else{			
				resultCode=1;
				message="Success";
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			message = "Couldn't establish connection with database. Check server configuration!";
			//message="Server Exception: "+e.getCause();
			//message="Server Exception: "+e.getMessage();
			resultCode=2;
		}		
		
		JsonParser<String,HashMap<String,String>> jsonResponse = new JsonParser<String, HashMap<String,String>>("viewProfile",resultCode,message,"ViewProfileResult",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
	public String updateProfile(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("firstName") String firstName,
			@RequestParam("middleName") String middleName,
			@RequestParam("lastName") String lastName,
			@RequestParam("secMail") String secMail,
			@RequestParam("secMobile") String secMobile){
		
		Byte resultCode=0;
		String message="";
		String result="";
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////
		log.debug("updateProfile");
		log.debug("Username:"+userName);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////
		if(userName==null)
			message="Your session has been expired! Please login.";
		else{
			
			try {
				if(userService.checkDuplicatePrimaryDetails(userName, secMail, secMobile)){

					userService.updateUserProfile(userName, firstName, middleName, lastName, secMail, secMobile);
					resultCode=1;
					message="Success";
				}
				else
					message = "Secondary emaild or phone number is same as primary";
			} catch (Exception e) {
				
				e.printStackTrace();
				message = "Unknown error occured! Please try again.";
				//message="Server Exception: "+e.getCause();
				//message="Server Exception: "+e.getMessage();
				resultCode=2;
			}
		}		
		
		JsonParser<String, String> jsonResponse = new JsonParser<String, String>("updateProfile",resultCode,message,"UpdateProfileResult",result);
		return jsonResponse.JsonResponseText();
	}
		
	@RequestMapping(value = "/changeProPic", method = RequestMethod.POST)
	public String changeProPic(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("file") String imageString){
		
		Byte resultCode=0;
		String message="";
		String result="";
		String temp;
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////
		log.debug("changeProPic");
		log.debug("Username:"+userName);
		log.debug(imageString);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////
		if(userName==null)
			message="Invalid session";
		else if(imageString.isEmpty())
			message="No file selected";
		else if((temp=EviService.insertProPic2(userName, imageString))!=null){
			
			result=temp;
			resultCode=1;
			message="Success";
		}
		else
			message="Upload failed";
		
		JsonParser<String, String> jsonResponse = new JsonParser<String, String>("changeProPic",resultCode,message,"changeProPicResult",result);
		return jsonResponse.JsonResponseText();
	}	
	
	@RequestMapping(value = "/deleteProPic", method = RequestMethod.POST)
	public String deleteProPic(HttpSession sessionObj,HttpServletResponse response){
		
		Byte resultCode=0;
		String message="";
		String result="";
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////
		log.debug("deleteProPic");
		log.debug("Username:"+userName);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////
		if(userName==null)
			message="Invalid session";
		else if(EviService.deleteProPic(userName)){
			
			resultCode=1;
			message="Success";
		}
		else
			message="Deletion failed";
		
		JsonParser<String, String> jsonResponse = new JsonParser<String, String>("deleteProPic",resultCode,message,"deleteProPicResult",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/viewAProfile", method = RequestMethod.POST)
	public String viewAProfile(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("userName") String reqUserName){
		
		Byte resultCode=0;
		String message="";
		HashMap<String,String> result=new HashMap<String,String>();
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////////
		log.debug("getRoles");
		log.debug("Username:"+userName);
		log.debug("reqUserName:"+reqUserName);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////////
		if(userName==null || !(boolean)sessionObj.getAttribute("admin"))
			message="Invalid session";
		else{
			
			HashMap<String,String> temp=userService.getUserProfile(reqUserName);
			if(temp==null)
				message="Server error";
			else{
				
				result=temp;
				resultCode=1;
				message="Success";
			}
		}
		
		JsonParser<String, HashMap<String,String>> jsonResponse = new JsonParser<String, HashMap<String,String>>("viewAProfile",resultCode,message,"viewAProfileResult",result);
		return jsonResponse.JsonResponseText();
	}

}
