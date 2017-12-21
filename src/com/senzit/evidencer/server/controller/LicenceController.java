package com.senzit.evidencer.server.controller;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.senzit.evidencer.server.service.LicenceService;
import com.senzit.evidencer.server.subservice.JsonParser;

@RestController
public class LicenceController {
	
	static Logger log = Logger.getLogger(LicenceController.class.getName());
	
	@Autowired 
	private LicenceService licenceService;
	
	@RequestMapping(value = "/getAllUserPrivileges", method = RequestMethod.POST)
	public String getAllUserPrivileges(HttpSession sessionObj,HttpServletResponse response){
		
		Byte resultCode=0;
		String message="";
		List<Hashtable<String, Object>> result=new ArrayList<Hashtable<String, Object>>();
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////////
		log.debug("getAllUserPrivileges");
		log.debug("Username:"+userName);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////////
		if(userName==null && !(boolean)sessionObj.getAttribute("admin"))
			message="Invalid session";
		else{
			
			try {
				result = licenceService.getAllUserPrivileges();
				resultCode=1;
				message="Success";
			} catch (Exception e) {
				
				e.printStackTrace();
				message="Server Exception: "+e.getCause();
				//message="Server Exception: "+e.getMessage();
				resultCode=2;
			}
		}
		
		JsonParser<String, List<Hashtable<String, Object>>> jsonResponse = new JsonParser<String, List<Hashtable<String, Object>>>("getAllUserPrivileges",resultCode,message,"allUserPrivilegeList",result);
		return jsonResponse.JsonResponseText();
	}

}
