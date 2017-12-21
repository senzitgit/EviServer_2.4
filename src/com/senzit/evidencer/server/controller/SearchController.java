package com.senzit.evidencer.server.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.senzit.evidencer.server.service.SearchService;
import com.senzit.evidencer.server.subservice.JsonParser;

@RestController
public class SearchController {
	
	static Logger log = Logger.getLogger(LoginController.class.getName());
	
	@Autowired 
	private SearchService searchService;
	
	@RequestMapping(value = "/simpleSearch", method = RequestMethod.POST)
	public String simpleSearch(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("caseNo") String caseNo){
		
		Byte resultCode=0;
		String message="";
		List<Integer> result=new ArrayList<Integer>();
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////////
		log.debug("simpleSearch");
		log.debug("Username:"+userName);
		log.debug("CaseNo:"+caseNo);
		log.debug("sessionId:"+sessionObj.getId());
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////////
		if(userName==null)
			message="Your session has been expired! Please login.";
		else{
			
			try {
				result=searchService.getSittingList(caseNo);
				if(result.isEmpty())
					message="Case information not found for provided case number.";
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
		}
		
		JsonParser<String, List<Integer>> jsonResponse = new JsonParser<String, List<Integer>>("simpleSearch",resultCode,message,"SittingList",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/sessionSearch", method = RequestMethod.POST)
	public String sessionSearch(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("caseNo") String caseNo,@RequestParam("sittingNo") int sittingNo){
		
		Byte resultCode=0;
		String message="";
		List<Integer> result=new ArrayList<Integer>();
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////////
		log.debug("sessionSearch");
		log.debug("Username:"+userName);
		log.debug("CaseNo:"+caseNo);
		log.debug("SittingNo:"+sittingNo);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////////
		if(userName==null)
			message="Your session has been expired! Please login.";
		else{
			
			try {
				result=searchService.getSessionList(caseNo, sittingNo);
				if(result.isEmpty())
					message="No case session found for provided case sitting.";
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
		}
		
		JsonParser<String, List<Integer>> jsonResponse = new JsonParser<String, List<Integer>>("sessionSearch",resultCode,message,"SessionList",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/advancedSearch", method = RequestMethod.POST)
	public String advancedSearch(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("caseNo") String caseNo,@RequestParam("caseTitle") String caseTitle,
			@RequestParam("caseType") String caseType,@RequestParam("caseDate") String caseDate){
		
		Byte resultCode=0;
		String message="";
		Properties result=new Properties();
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////////
		log.debug("advancedSearch");
		log.debug("Username:"+userName);
		log.debug("CaseNo:"+caseNo);
		log.debug("CaseTitle:"+caseTitle);
		log.debug("CaseType:"+caseType);
		log.debug("CaseDate:"+caseDate);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////////
		if(userName==null)
			message="Your session has been expired! Please login.";
		else{
			
			try {
				result=searchService.getAdvancedSearchResult(caseNo, caseTitle, caseType, caseDate);
				if(result.isEmpty())
					message="Case information not found for provided search criteria.";
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
		}
		
		JsonParser<String, Properties> jsonResponse = new JsonParser<String, Properties>("advancedSearch",resultCode,message,"AdvancedResult",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/simpleDateSearch", method = RequestMethod.POST)
	public String simpleDateSearch(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("caseNo") String caseNo,
			@RequestParam("date") String dateString){
		
		Byte resultCode=0;
		String message="";
		List<Integer> result=new ArrayList<Integer>();
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////////
		log.debug("simpleDateSearch");
		log.debug("Username:"+userName);
		log.debug("CaseNo:"+caseNo);
		log.debug("sessionId:"+sessionObj.getId());
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////////
		if(userName==null)
			message="Invalid session";
		else{
			
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date=null;
			try {
				date= formatter.parse(dateString);
				result=searchService.getSittingList(caseNo,date);
				if(result.isEmpty())
					message="No data found";
				else{
					
					resultCode=1;
					message="Success";
				}
			} catch (Exception e) {
				
				e.printStackTrace();
				message="Server Exception: "+e.getCause();
				//message="Server Exception: "+e.getMessage();
				resultCode=2;
			}
			
			result=searchService.getSittingList(caseNo,date);
			if(result.isEmpty())
				message="No data found";
			else{
				
				resultCode=1;
				message="Success";
			}
		}
		
		JsonParser<String, List<Integer>> jsonResponse = new JsonParser<String, List<Integer>>("simpleDateSearch",resultCode,message,"SittingList",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/sessionDateSearch", method = RequestMethod.POST)
	public String sessionDateSearch(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("caseNo") String caseNo,@RequestParam("sittingNo") int sittingNo,
			@RequestParam("date") String dateString){
		
		Byte resultCode=0;
		String message="";
		List<Integer> result=new ArrayList<Integer>();
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////////
		log.debug("sessionDateSearch");
		log.debug("Username:"+userName);
		log.debug("CaseNo:"+caseNo);
		log.debug("SittingNo:"+sittingNo);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////////
		if(userName==null)
			message="Invalid session";
		else{
			
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date=null;
			try {
				date= formatter.parse(dateString);
				result=searchService.getSessionList(caseNo, sittingNo,date);
				if(result.isEmpty())
					message="No data found";
				else{
					resultCode=1;
					message="Success";
				}
			} catch (Exception e) {
				
				e.printStackTrace();
				message="Server Exception: "+e.getCause();
				//message="Server Exception: "+e.getMessage();
				resultCode=2;
			}
			
		}
		
		JsonParser<String, List<Integer>> jsonResponse = new JsonParser<String, List<Integer>>("sessionDateSearch",resultCode,message,"SessionList",result);
		return jsonResponse.JsonResponseText();
	}

}
