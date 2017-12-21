package com.senzit.evidencer.server.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.senzit.evidencer.server.service.LEService;
import com.senzit.evidencer.server.subservice.JsonParser;

@RestController
public class LEController {
	
	static Logger log = Logger.getLogger(LoginController.class.getName());
	
	@Autowired 
	private LEService leService;
	
	// Last minute change as per Portal implementation change
	@RequestMapping(value = "/insertLoc", method = RequestMethod.POST)
	public String insertLoc(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("userName") String userName,
			@RequestParam("locPath") String locPath){
		
		Byte resultCode=0;
		String message="";
		String result="";
		///////////////////////////
		log.debug("insertLoc");
		log.debug("Username:"+userName);
		log.debug(locPath);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////////
		try {
			if(leService.insertLocation(userName, locPath)==null)
				message = "L&E location updation failed";
			else{
				message = "Success";
				resultCode=1;
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			message = "Couldn't establish connection with database. Check server configuration!";
			//message="Server Exception: "+e.getCause();
			//message="Server Exception: "+e.getMessage();
			resultCode=2;
		}		
		JsonParser<String, String> jsonResponse = new JsonParser<String, String>("insertLoc",resultCode,message,"insertLocResult",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/getLoc", method = RequestMethod.POST)
	public String getLoc(HttpSession sessionObj,HttpServletResponse response){
		
		Byte resultCode=0;
		String message="";
		String result="";
		String userName = (String) sessionObj.getAttribute("userName");
		///////////////////////////
		log.debug("getLoc");
		log.debug("Username:"+userName);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////////
		try {
			String temp = leService.getLocation(userName);
			if(temp==null)
				message="No location defined";
			else{
				result=temp;
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
		JsonParser<String, String> jsonResponse = new JsonParser<String, String>("getLoc",resultCode,message,"location",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/updateLoc", method = RequestMethod.POST)
	public String updateLoc(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("locPath") String locPath){
		
		Byte resultCode=0;
		String message="";
		String result="";
		String userName = (String) sessionObj.getAttribute("userName");
		///////////////////////////
		log.debug("updateLoc");
		log.debug("Username:"+userName);
		log.debug(locPath);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////////
		try {
			if(leService.updateLocation(userName, locPath)==0)
				message = "L&E location updation failed";
			else{
				message = "Success";
				resultCode=1;
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			message = "Couldn't establish connection with database. Check server configuration!";
			//message="Server Exception: "+e.getCause();
			//message="Server Exception: "+e.getMessage();
			resultCode=2;
		}		
		JsonParser<String, String> jsonResponse = new JsonParser<String, String>("updateLoc",resultCode,message,"updateLocResult",result);
		return jsonResponse.JsonResponseText();
	}

}
