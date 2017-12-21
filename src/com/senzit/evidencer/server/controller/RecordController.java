package com.senzit.evidencer.server.controller;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.senzit.evidencer.server.service.RecordService;
import com.senzit.evidencer.server.subservice.EviService;
import com.senzit.evidencer.server.subservice.JsonParser;
import com.senzit.evidencer.server.subservice.RebbonService;

@RestController
public class RecordController {
	
	static Logger log = Logger.getLogger(RecordController.class.getName());
	
	@Autowired 
	private RecordService recordService;
	
	@RequestMapping(value = "/checkCase", method = RequestMethod.POST)
	public String checkCase(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("caseNo") String caseNo){
		
		Byte resultCode=0;
		String message="";
		Properties result=new Properties();
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////
		log.debug("checkCase");
		log.debug("Username:"+userName);
		log.debug("Caseno:"+caseNo);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////
		if(userName==null)
			message="Your session has been expired! Please login.";
		else{
			//if(!EviService.getRecordStatus()){
				
				RebbonService rebbonService=new RebbonService();
				if(rebbonService.startRebbon()){
					
					try {
						Object obj[]=recordService.checkCase(caseNo);
						message=obj[0].toString();
						result=(Properties) obj[1];
						resultCode=1;
					} catch (Exception e) {
						
						e.printStackTrace();
						message = "Couldn't establish connection with database. Check server configuration!";
						//message="Server Exception: "+e.getCause();
						//message="Server Exception: "+e.getMessage();
						resultCode=2;
					}
				}
				else
					message="Couldn't communicate with Rebbon! Check if Rebbon is up and running.";
//			}
//			else
//				message="Recording already started";
		}
		
		JsonParser<String, Properties> jsonResponse = new JsonParser<String, Properties>("checkCase",resultCode,message,"CaseCheckResult",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/newCase", method = RequestMethod.POST)
	public String createNewCaseEvent(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("IDE") String ide,
			@RequestParam("newCaseJson") String newCaseJson){
		
		Byte resultCode=0;
		String message="";
		Hashtable<String,Integer> result=new Hashtable<String,Integer>();
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////
		log.debug("newCase");
		log.debug("Username:"+userName);
		log.debug(ide);
		log.debug(newCaseJson);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////
		if(userName==null)
			message="Your session has been expired! Please login.";
		else{		
			
			if(ide.equals("WorkLight"))
				newCaseJson=EviService.jsonForWorklight(newCaseJson);
			try {
				JSONObject jsonData = new JSONObject(newCaseJson);
				Integer eventId=recordService.createNewCaseEvent(jsonData);
				if(eventId!=null){
					
					//Create Working folder
					String workingFolderPath=EviService.createWorkingFolder(eventId, "Recorder", userName);
					/////////////////////////////
					log.debug("WorkingFolder:"+workingFolderPath);
					/////////////////////////////
					sessionObj.setAttribute("workingFolderPath", workingFolderPath);
					sessionObj.setAttribute("caseNo", jsonData.getString("caseNo"));
					sessionObj.setAttribute("caseEventId", eventId);
					//EviService.setRecordStatus(true);
					result.put("caseEventId", eventId);
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
		}
		
		JsonParser<String, Hashtable<String,Integer>> jsonResponse = new JsonParser<String, Hashtable<String,Integer>>("newCase",resultCode,message,"NewCaseEventResult",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/forceStartRecord", method = RequestMethod.POST)
	public String startRecord(HttpSession sessionObj,HttpServletResponse response){
		
		Byte resultCode=0;
		String message="";
		Properties result=new Properties();
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////
		log.debug("forceStartRecord");
		log.debug("Username:"+userName);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////
		if(userName==null)
			message="Your session has been expired! Please login.";
		else{
			
			RebbonService rebbonService=new RebbonService();
			Properties links=rebbonService.startRebbonRecord();
			if(links==null)
				message="Couldn't communicate with Rebbon! Check if Rebbon is up and running.";
			else{
				
				result=links;
				resultCode=1;
				message="Success";
			}
		}
		
		JsonParser<String, Properties> jsonResponse = new JsonParser<String, Properties>("forceStartRecord",resultCode,message,"StreamDetail",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/stopRecord", method = RequestMethod.POST)
	public String stopRecord(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("IDE") String ide,
			@RequestParam("toneAnalyze") String toneAnalyze,
			@RequestParam("sentimentalAnalyze") String sentimentalAnalyze,
			@RequestParam("initiatedFrom") String initiatedFrom,
			@RequestParam("stopRecordJson") String stopRecordJson){
		
		Byte resultCode=0;
		String message="";
		String result="";
		
		String userName=(String)sessionObj.getAttribute("userName");
		String caseNo=(String)sessionObj.getAttribute("caseNo");
		Integer caseEventId=(Integer)sessionObj.getAttribute("caseEventId");
		String workingFolder=(String)sessionObj.getAttribute("workingFolderPath");
		///////////////////////
		log.debug("stopRecord");
		log.debug("Username:"+userName);
		log.debug("CaseNo:"+caseNo);
		log.debug("CaseEventId:"+caseEventId);
		log.debug("WorkingFolder:"+workingFolder);
		log.debug(ide);
		log.debug(stopRecordJson);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////
		
		if(userName==null || caseNo==null || caseEventId==null || workingFolder==null)
			message="Your session has been expired! Please login.";
		else{
			
			if(ide.equals("WorkLight"))
				stopRecordJson=EviService.jsonForWorklight(stopRecordJson);
			
			RebbonService rebbonService=new RebbonService();
			boolean flag=false;
			try {
				flag=rebbonService.stopRebbon(userName,caseEventId,caseNo, "workingFolder");
				

					JSONObject jsonData = new JSONObject(stopRecordJson);
					recordService.updateDb(caseNo, caseEventId, jsonData,toneAnalyze,sentimentalAnalyze,initiatedFrom);						
					sessionObj.removeAttribute("caseNo");
					sessionObj.removeAttribute("workingFolderPath");
//					EviService.setRecordStatus(false);
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
		JsonParser<String, String> jsonResponse = new JsonParser<String, String>("stopRecord",resultCode,message,"StopRecordResult",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/getLiveSessions", method = RequestMethod.POST)
	public String getLiveSessions(HttpSession sessionObj,HttpServletResponse response){
		
		Byte resultCode=0;
		String message="";
		List<Hashtable<String, Object>> result=new ArrayList<Hashtable<String, Object>>();
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////
		log.debug("getLiveSessions");
		log.debug("Username:"+userName);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////
		
		if(userName==null)
			message="Invalid session";
		else{

			try {
				result=recordService.getLiveSessionDetails();
				resultCode=1;
				message="Success";
			} catch (Exception e) {
				
				e.printStackTrace();
				message="Server Exception: "+e.getCause();
				//message="Server Exception: "+e.getMessage();
				resultCode=2;
			}
		}
		
		JsonParser<String, List<Hashtable<String, Object>>> jsonResponse = new JsonParser<String, List<Hashtable<String, Object>>>("getLiveSessions",resultCode,message,"LiveSessionsList",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/getLiveDetails", method = RequestMethod.POST)
	public String getLiveMediaLinks(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("caseEventId") Integer caseEventId){
		
		Byte resultCode=0;
		String message="";
		Hashtable<String, Object> result=new Hashtable<String, Object>();
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////
		log.debug("getLiveDetails");
		log.debug("Username:"+userName);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////
		
		if(userName==null)
			message="Invalid session";
		else{
			try {
				Hashtable<String, Object> temp=recordService.getLiveCaseDetails(caseEventId);
				if(temp==null)
					message="Server error";
				else{
					
					resultCode=1;
					message="Success";
					result=temp;
					
				}
			} catch (Exception e) {
				
				e.printStackTrace();
				message="Server Exception: "+e.getCause();
				//message="Server Exception: "+e.getMessage();
				resultCode=2;
			}
		}
		
		JsonParser<String, Hashtable<String, Object>> jsonResponse = new JsonParser<String, Hashtable<String, Object>>("getLiveDetails",resultCode,message,"getLiveDetailsResult",result);
		return jsonResponse.JsonResponseText();
	}
	
//	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
//	public void uploadFile(HttpSession sessionObj){
//		
//			
//		String userName=(String)sessionObj.getAttribute("userName");
//		Integer caseEventId=(Integer)sessionObj.getAttribute("caseEventId");
//		///////////////////////
//		log.debug("getLiveDetails");
//		log.debug("Username:"+userName);
//		log.debug("caseEventId:"+caseEventId);
//		//////////////////////
//		if(userName==null)
//			log.debug("Invalid session");
//		else{
//			
//			RebbonService rebbon=new RebbonService();
//			rebbon.uploadFile(caseEventId);
//			sessionObj.removeAttribute("caseEventId");
//		}
//	}

}
