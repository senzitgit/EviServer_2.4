package com.senzit.evidencer.server.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.senzit.evidencer.server.service.PlayerService;
import com.senzit.evidencer.server.subservice.JsonParser;
import com.senzit.evidencer.server.subservice.RebbonService;

@RestController
public class PlayerController {
	
	static Logger log = Logger.getLogger(LoginController.class.getName());
	
	@Autowired 
	private PlayerService playerService;
	
	@RequestMapping(value = "/getPlayerInfo", method = RequestMethod.POST)
	public String getPlayerInfo(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("caseNo") String caseNo,@RequestParam("sittingNo") int sittingNo,
			@RequestParam("sessionNo") int sessionNo){
		
		Byte resultCode=0;
		String message="";
		HashMap<String,Object> result=new HashMap<String,Object>();
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////////
		log.debug("getPlayerInfo");
		log.debug("Username:"+userName);
		log.debug("CaseNo:"+caseNo);
		log.debug("SittingNo:"+sittingNo);
		log.debug("SessionNo"+sessionNo);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////////
		if(userName==null)
			message="Your session has been expired! Please login.";
		else{
			
			try {
				HashMap<String,Object> caseDetails=playerService.getCaseEventDetails(caseNo, sittingNo, sessionNo);
				System.err.println("caseDetails"+caseDetails);
				if(caseDetails!=null){
					
					int eventId=(int)caseDetails.get("caseEventId");
					RebbonService obj=new RebbonService();
					Hashtable<String,Object> avLinks=obj.getAVLinks(eventId,userName);
					
					
					System.err.println("Avlinks"+avLinks);
					if(avLinks!=null){
						
						if(avLinks.isEmpty())
							message = "Missing case files! Case media files are not properly stored.";
						else{

							result.put("caseDetails", caseDetails);
							result.put("avLinks", avLinks);
							resultCode=1;
							message="Success";
						}
					}
					else
						message="Couldn't communicate with Rebbon! Check if Rebbon is up and running.";
				}
				else
					message="Missing case files! Case media files are not properly stored.";
			} catch (Exception e) {
				
				e.printStackTrace();
				message = "Couldn't establish connection with database. Check server configuration!";				//message="Server Exception: "+e.getCause();
				//message="Server Exception: "+e.getMessage();
				resultCode=2;
			}
			
		}
		
		JsonParser<String, HashMap<String,Object>> jsonResponse = new JsonParser<String, HashMap<String,Object>>("getPlayerInfo",resultCode,message,"PlayerInfoResult",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/getAttachment", method = RequestMethod.POST)
	public String getAttachment(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("caseEventId") int caseEventId){
		
		Byte resultCode=0;
		String message="";
		List<Hashtable<String, Object>> result=new ArrayList<Hashtable<String, Object>>();
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////////
		log.debug("getAttachment");
		log.debug("Username:"+userName);
		log.debug("CaseEventId:"+caseEventId);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////////
		if(userName==null)
			message="Your session has been expired! Please login.";
		else{			
			
			try {
				List<Hashtable<String, Object>> temp=playerService.getAttachmentDetails(caseEventId,userName);
				if(temp!=null){
					
					result=temp;
					resultCode=1;
					message="Success";
				}
				else
					message="Couldn't communicate with Rebbon! Check if Rebbon is up and running.";
			} catch (Exception e) {
				
				e.printStackTrace();
				message = "Couldn't establish connection with database. Check server configuration!";
				//message="Server Exception: "+e.getCause();
				//message="Server Exception: "+e.getMessage();
				resultCode=2;
			}
		}		
		
		JsonParser<String, List<Hashtable<String, Object>>> jsonResponse = new JsonParser<String, List<Hashtable<String, Object>>>("getAttachment",resultCode,message,"AttachmentList",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/getPrivateNote", method = RequestMethod.POST)
	public String getPlayerInfo(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("caseEventId") int caseEventId){
		
		Byte resultCode=0;
		String message="";
		List<Hashtable<String, Object>> result=new ArrayList<Hashtable<String, Object>>();
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////////
		log.debug("getPrivateNote");
		log.debug("Username:"+userName);
		log.debug("CaseEventId:"+caseEventId);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////////
		if(userName==null)	// Also check sessionObj.removeAttribute("privateNoteUser");
			message="Your session has been expired! Please login.";
		else{
			
			try {
				result=playerService.getPrivateNoteDetails(caseEventId);
				sessionObj.removeAttribute("privateNoteUser");
				resultCode=1;
				message="Success";
			} catch (Exception e) {
				
				e.printStackTrace();
				message = "Couldn't establish connection with database. Check server configuration!";
				//message="Server Exception: "+e.getCause();
				//message="Server Exception: "+e.getMessage();
				resultCode=2;
			}
		}		
		
		JsonParser<String, List<Hashtable<String, Object>>> jsonResponse = new JsonParser<String, List<Hashtable<String, Object>>>("getPrivateNote",resultCode,message,"PrivateNoteList",result);
		return jsonResponse.JsonResponseText();
	}
	
//	@RequestMapping(value = "/clearWorkingFolder", method = RequestMethod.POST)
//	public String clearWorkingFolder(HttpSession sessionObj,
//			@RequestParam("caseEventId") int caseEventId){
//		
//		Byte resultCode=0;
//		String message="";
//		List<Hashtable<String, Object>> result=new ArrayList<Hashtable<String, Object>>();
//		
//		String userName=(String)sessionObj.getAttribute("userName");
//		///////////////////////////
//		log.debug("clearWorkingFolder");
//		log.debug("Username:"+userName);
//		log.debug("CaseEventId:"+caseEventId);
//		//////////////////////////
//		if(userName==null)
//			message="Invalid session";
//		else{
//			
//			RebbonService rebbon=new RebbonService();
//			rebbon.clear(caseEventId);
//		}		
//		
//		JsonParser<String, List<Hashtable<String, Object>>> jsonResponse = new JsonParser<String, List<Hashtable<String, Object>>>("getPrivateNote",resultCode,message,"PrivateNoteList",result);
//		return jsonResponse.JsonResponseText();
//	}

}
