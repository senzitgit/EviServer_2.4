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
import org.springframework.web.multipart.MultipartFile;

import com.senzit.evidencer.server.service.AuthService;
import com.senzit.evidencer.server.service.FileService;
import com.senzit.evidencer.server.service.UserService;
import com.senzit.evidencer.server.subservice.JsonParser;

@RestController
public class RecordFileController {
	
	static Logger log = Logger.getLogger(RecordController.class.getName());
	
	@Autowired 
	private FileService fileService;
	@Autowired 
	private UserService userService;
	@Autowired 
	private AuthService authService;
	
	//////////////////////////////////////////////////
	//	Edited for not checking session; CLIENT problems!! 
	/////////////////////////////////////////////////
	@RequestMapping(value = "/captureAttachment", method = RequestMethod.POST)
	public String captureAttachment(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("caseEventId") Integer caseEventId,
			@RequestParam("userName") String userName,
			@RequestParam("file") MultipartFile file,
			@RequestParam("attachmentName") String attachmentName,
			@RequestParam("attachmentDesc") String attachmentDesc){
		
		Byte resultCode=0;
		String message="";
		String result="";
		
//		String userName=(String)sessionObj.getAttribute("userName");
		String caseNo=(String)sessionObj.getAttribute("caseNo");
//		Integer caseEventId=(Integer)sessionObj.getAttribute("caseEventId");
		///////////////////////
		log.debug("captureAttachment");
		log.debug("Username:"+userName);
		log.debug("CaseNo:"+caseNo);
		log.debug("CaseEventId:"+caseEventId);
		log.debug("AttachmentName:"+attachmentName);
		log.debug("AttachDesc:"+attachmentDesc);
		log.debug("FileName:"+file.getOriginalFilename());
		log.debug("IsFile"+!file.isEmpty());
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////
		try {
			if(userName==null || caseEventId==null)		// No session Check actually
				message="Invalid session";
			else if(fileService.saveAttachment(file, System.currentTimeMillis()+file.getOriginalFilename(), 
					attachmentName, attachmentDesc, userName, caseEventId)){
				
				resultCode=1;
				message="Success";
			}
			else
				message="Couldn't save attachment! Please upload again.";
		} catch (Exception e) {
			
			e.printStackTrace();
			message = "Couldn't establish connection with database. Check server configuration!";
			//message="Server Exception: "+e.getCause();
			//message="Server Exception: "+e.getMessage();
			resultCode=2;
			
			// NEED TO DELETE SAVED FILE			
			
			
		}
		
		JsonParser<String, String> jsonResponse = new JsonParser<String, String>("captureAttachment",resultCode,message,"CaptureAttachmentResult",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/privateNoteAuth", method = RequestMethod.POST)
	public String captureAttachment(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("userName") String userName,@RequestParam("password") String password){
		
		Byte resultCode=0;
		String message="";
		HashMap<String,String> result=new HashMap<String,String>();
		
		String userNameSession=(String)sessionObj.getAttribute("userName");
		///////////////////////
		log.debug("privateNoteAuth");
		log.debug("Username:"+userName);
		log.debug("UsernameSession:"+userNameSession);
		log.debug(password);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////
		try {
			if(userNameSession==null)
				message="Your session has been expired! Please login.";
			else if(authService.authenticate(userName, password)){
				
				if(authService.checkPrivateNotePrivilege(userName)>0){				

					result.put("roleName", userService.getUserRoleName(userName));
					resultCode=1;
					message="Success";
				}
				else
					message="Your account doesn't have private note privilages";
			}
			else
				message="Username and password doesn't match! Please try again.";
		} catch (Exception e) {
			
			e.printStackTrace();
			message = "Couldn't establish connection with database. Check server configuration!";
			//message="Server Exception: "+e.getCause();
			//message="Server Exception: "+e.getMessage();
			resultCode=2;
		}
		
		JsonParser<String, HashMap<String,String>> jsonResponse = new JsonParser<String, HashMap<String,String>>("privateNoteAuth",resultCode,message,"PrivateNoteResult",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/enterPrivateNote", method = RequestMethod.POST)
	public String insertPrivateNote(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("privateNoteUserName") String privateNoteUser,
			@RequestParam("privateNote") String privateNote){
		
		Byte resultCode=0;
		String message="";
		String result="";
		
		String userName=(String)sessionObj.getAttribute("userName");
		Integer caseEventId=(Integer)sessionObj.getAttribute("caseEventId");
		///////////////////////
		log.debug("enterPrivateNote");
		log.debug("Username:"+userName);
		log.debug("PrivateNoteUser:"+privateNoteUser);
		log.debug("CaseEventId:"+caseEventId);
		log.debug(privateNote);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////
		try {
			if(userName==null || caseEventId==null)
				message="Your session has been expired! Please login.";
			else if(fileService.savePrivateNote(privateNoteUser, caseEventId, privateNote)){
				
				resultCode=1;
				message="Success";
			}
			else
				message="Unknown error! Please try again";
		} catch (Exception e) {
			
			e.printStackTrace();
			message = "Couldn't establish connection with database. Check server configuration!";
			//message="Server Exception: "+e.getCause();
			//message="Server Exception: "+e.getMessage();
			resultCode=2;
		}
		
		JsonParser<String, String> jsonResponse = new JsonParser<String, String>("enterPrivateNote",resultCode,message,"PrivateNoteResult",result);
		return jsonResponse.JsonResponseText();
	}
	
}
