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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.senzit.evidencer.server.service.PortalService;
import com.senzit.evidencer.server.service.RegService;
import com.senzit.evidencer.server.subservice.JsonParser;

@RestController
public class PortalController {
	
	static Logger log = Logger.getLogger(LoginController.class.getName());
	@Autowired 
	private PortalService portalService;
	@Autowired 
	private RegService regService;
	
//	@RequestMapping(value = "/getLocations", method = RequestMethod.POST)
//	public String getLocations(HttpSession sessionObj){
//		
//		Byte resultCode=0;
//		String message="";
//		List<Hashtable<String,Object>> result=new ArrayList<Hashtable<String,Object>>();
//		
//		String userName=(String)sessionObj.getAttribute("userName");
//		///////////////////////////
//		log.debug("getLocations");
//		log.debug("Username:"+userName);
//		//////////////////////////
//		if(userName==null)
//			message="Invalid session";
//		else{
//			
//			try {
//				result=portalService.getLocationList();
//				resultCode=1;
//				message="Success";
//			} catch (Exception e) {
//				
//				e.printStackTrace();
//				message="Server Exception: "+e.getCause();
//				//message="Server Exception: "+e.getMessage();
//				resultCode=2;
//			}
//		}
//		
//		JsonParser<String, List<Hashtable<String,Object>>> jsonResponse = new JsonParser<String, List<Hashtable<String,Object>>>("getLocations",resultCode,message,"locationList",result);
//		return jsonResponse.JsonResponseText();
//	}
//	
	@RequestMapping(value = "/getCaseTypes", method = RequestMethod.POST)
	public String getCaseTypes(HttpSession sessionObj,HttpServletResponse response){
		
		Byte resultCode=0;
		String message="";
		List<Hashtable<String,Object>> result=new ArrayList<Hashtable<String,Object>>();
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////////
		log.debug("getCaseTypes");
		log.debug("Username:"+userName);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////////
		if(userName==null)
			message="Invalid session";
		else{
			
			try {
				result=portalService.getCaseTypeList();
				resultCode=1;
				message="Success";
			} catch (Exception e) {
				
				e.printStackTrace();
				message="Server Exception: "+e.getCause();
				//message="Server Exception: "+e.getMessage();
				resultCode=2;
			}
		}
		
		JsonParser<String, List<Hashtable<String,Object>>> jsonResponse = new JsonParser<String, List<Hashtable<String,Object>>>("getCaseTypes",resultCode,message,"caseTypeList",result);
		return jsonResponse.JsonResponseText();
	}
//	
//	@RequestMapping(value = "/getRoles", method = RequestMethod.POST)
//	public String getRole(HttpSession sessionObj){
//		
//		Byte resultCode=0;
//		String message="";
//		List<Hashtable<String,Object>> result=new ArrayList<Hashtable<String,Object>>();
//		
//		String userName=(String)sessionObj.getAttribute("userName");
//		///////////////////////////
//		log.debug("getRoles");
//		log.debug("Username:"+userName);
//		//////////////////////////
//		if(userName==null)
//			message="Invalid session";
//		else{
//			
//			try {
//				result=portalService.getRoleList();
//				resultCode=1;
//				message="Success";
//			} catch (Exception e) {
//				
//				e.printStackTrace();
//				message="Server Exception: "+e.getCause();
//				//message="Server Exception: "+e.getMessage();
//				resultCode=2;
//			}
//		}
//		
//		JsonParser<String, List<Hashtable<String,Object>>> jsonResponse = new JsonParser<String, List<Hashtable<String,Object>>>("getRoles",resultCode,message,"roleList",result);
//		return jsonResponse.JsonResponseText();
//	}
//	
//	@RequestMapping(value = "/getPrivileges", method = RequestMethod.POST)
//	public String getPrivileges(HttpSession sessionObj){
//		
//		Byte resultCode=0;
//		String message="";
//		List<Hashtable<String,Object>> result=new ArrayList<Hashtable<String,Object>>();
//		
//		String userName=(String)sessionObj.getAttribute("userName");
//		///////////////////////////
//		log.debug("getPrivileges");
//		log.debug("Username:"+userName);
//		//////////////////////////
//		if(userName==null)
//			message="Invalid session";
//		else{
//			
//			try {
//				result=portalService.getPrivilegeList();
//				resultCode=1;
//				message="Success";
//			} catch (Exception e) {
//				
//				e.printStackTrace();
//				message="Server Exception: "+e.getCause();
//				//message="Server Exception: "+e.getMessage();
//				resultCode=2;
//			}
//		}
//		
//		JsonParser<String, List<Hashtable<String,Object>>> jsonResponse = new JsonParser<String, List<Hashtable<String,Object>>>("getPrivileges",resultCode,message,"privilegeList",result);
//		return jsonResponse.JsonResponseText();
//	}
//	
//	@RequestMapping(value = "/getCourts", method = RequestMethod.POST)
//	public String getCourts(HttpSession sessionObj){
//		
//		Byte resultCode=0;
//		String message="";
//		List<Hashtable<String,Object>> result=new ArrayList<Hashtable<String,Object>>();
//		
//		String userName=(String)sessionObj.getAttribute("userName");
//		///////////////////////////
//		log.debug("getCourts");
//		log.debug("Username:"+userName);
//		//////////////////////////
//		if(userName==null)
//			message="Invalid session";
//		else{
//			
//			try {
//				result=portalService.getCourtList();
//				resultCode=1;
//				message="Success";
//			} catch (Exception e) {
//				
//				e.printStackTrace();
//				message="Server Exception: "+e.getCause();
//				//message="Server Exception: "+e.getMessage();
//				resultCode=2;
//			}
//		}
//		
//		JsonParser<String, List<Hashtable<String,Object>>> jsonResponse = new JsonParser<String, List<Hashtable<String,Object>>>("getCourts",resultCode,message,"courtList",result);
//		return jsonResponse.JsonResponseText();
//	}
//	
//	@RequestMapping(value = "/getCaseStatuses", method = RequestMethod.POST)
//	public String getCaseStatuses(HttpSession sessionObj){
//		
//		Byte resultCode=0;
//		String message="";
//		List<Hashtable<String,Object>> result=new ArrayList<Hashtable<String,Object>>();
//		
//		String userName=(String)sessionObj.getAttribute("userName");
//		///////////////////////////
//		log.debug("getCaseStatuses");
//		log.debug("Username:"+userName);
//		//////////////////////////
//		if(userName==null)
//			message="Invalid session";
//		else{
//			
//			try {
//				result=portalService.getCaseStatusList();
//				resultCode=1;
//				message="Success";
//			} catch (Exception e) {
//				
//				e.printStackTrace();
//				message="Server Exception: "+e.getCause();
//				//message="Server Exception: "+e.getMessage();
//				resultCode=2;
//			}
//		}
//		
//		JsonParser<String, List<Hashtable<String,Object>>> jsonResponse = new JsonParser<String, List<Hashtable<String,Object>>>("getCaseStatuses",resultCode,message,"statusList",result);
//		return jsonResponse.JsonResponseText();
//	}
	
	// New common request for fetching all above details for portal
	@RequestMapping(value = "/getAllDetails", method = RequestMethod.POST)
	public String getAllDetails(HttpSession sessionObj,HttpServletResponse response){
		
		Byte resultCode=0;
		String message="";
		Hashtable<String,List<Hashtable<String,Object>>> result=new Hashtable<String,List<Hashtable<String,Object>>>();
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////////
		log.debug("getAllDetails");
		log.debug("Username:"+userName);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////////
		if(userName==null)
			message="Invalid session";
		else{
			
			try {
				result.put("locationList", portalService.getLocationList());
				result.put("caseTypeList",portalService.getCaseTypeList());
				result.put("roleList",portalService.getRoleList());
				result.put("privilegeList",portalService.getPrivilegeList());
				result.put("courtList",portalService.getCourtList());
				result.put("statusList",portalService.getCaseStatusList());
				
				result.put("partialRegList",regService.getPartialRegisters());
				resultCode=1;
				message="Success";
			} catch (Exception e) {
				
				e.printStackTrace();
				message="Server Exception: "+e.getCause();
				//message="Server Exception: "+e.getMessage();
				resultCode=2;
			}
		}
		
		JsonParser<String, Hashtable<String,List<Hashtable<String,Object>>>> jsonResponse = new JsonParser<String, Hashtable<String,List<Hashtable<String,Object>>>>("getAllDetails",resultCode,message,"detailsList",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/addNewCourt", method = RequestMethod.POST)
	public String addNewCourt(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("courtName") String courtName,
			@RequestParam("courtDetails") String courtDetails,
			@RequestParam("locationId") int locationId){
		
		Byte resultCode=0;
		String message="";
		String result="";
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////////
		log.debug("addNewCourt");
		log.debug("Username:"+userName);
		log.debug("courtName:"+courtName);
		log.debug("courtDetails:"+courtDetails);
		log.debug("locationId:"+locationId);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////////
		if(userName==null && !(boolean)sessionObj.getAttribute("admin"))
			message="Invalid session";
		else{
			
			try {
				if(portalService.insertNewCourt(courtName, courtDetails, locationId)==null)
					message="Server error";
				else{
					
					message="Success";
					resultCode=1;
				}
			} catch (Exception e) {
				
				e.printStackTrace();
				message="Server Exception: "+e.getCause();
				//message="Server Exception: "+e.getMessage();
				resultCode=2;
			}
		}
		
		JsonParser<String, String> jsonResponse = new JsonParser<String, String>("addNewCourt",resultCode,message,"addNewCourtResult",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/addNewLocation", method = RequestMethod.POST)
	public String addNewCourt(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("locationName") String locationName,
			@RequestParam("locationDesc") String locationDesc){
		
		Byte resultCode=0;
		String message="";
		String result="";
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////////
		log.debug("addNewLocation");
		log.debug("Username:"+userName);
		log.debug("locationName:"+locationName);
		log.debug("locationDesc:"+locationDesc);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////////
		if(userName==null && !(boolean)sessionObj.getAttribute("admin"))
			message="Invalid session";
		else{
			
			try {
				if(portalService.insertNewLocation(locationName, locationDesc)==null)
					message="Server error";
				else{
					
					message="Success";
					resultCode=1;
				}
			} catch (Exception e) {
				
				e.printStackTrace();
				message="Server Exception: "+e.getCause();
				//message="Server Exception: "+e.getMessage();
				resultCode=2;
			}
		}
		
		JsonParser<String, String> jsonResponse = new JsonParser<String, String>("addNewLocation",resultCode,message,"addNewLocationResult",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/addNewCaseType", method = RequestMethod.POST)
	public String addNewCaseType(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("caseType") String caseType,
			@RequestParam("typeDesc") String typeDesc){
		
		Byte resultCode=0;
		String message="";
		String result="";
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////////
		log.debug("addNewCaseType");
		log.debug("Username:"+userName);
		log.debug("caseType:"+caseType);
		log.debug("typeDesc:"+typeDesc);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////////
		if(userName==null && !(boolean)sessionObj.getAttribute("admin"))
			message="Invalid session";
		else{
			
			try {
				if(portalService.insertNewCaseType(caseType, typeDesc)==null)
					message="Server error";
				else{
					
					message="Success";
					resultCode=1;
				}
			} catch (Exception e) {
				
				e.printStackTrace();
				message="Server Exception: "+e.getCause();
				//message="Server Exception: "+e.getMessage();
				resultCode=2;
			}
		}
		
		JsonParser<String, String> jsonResponse = new JsonParser<String, String>("addNewCaseType",resultCode,message,"addNewCaseTypeResult",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/addNewCaseStatus", method = RequestMethod.POST)
	public String addNewCaseStatus(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("caseStatus") String caseStatus,
			@RequestParam("statusDesc") String statusDesc){
		
		Byte resultCode=0;
		String message="";
		String result="";
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////////
		log.debug("addNewCaseStatus");
		log.debug("Username:"+userName);
		log.debug("caseStatus:"+caseStatus);
		log.debug("statusDesc:"+statusDesc);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////////
		if(userName==null && !(boolean)sessionObj.getAttribute("admin"))
			message="Invalid session";
		else{
			
			try {
				if(portalService.insertNewCaseStatus(caseStatus, statusDesc)==null)
					message="Server error";
				else{
					
					message="Success";
					resultCode=1;
				}
			} catch (Exception e) {
				
				e.printStackTrace();
				message="Server Exception: "+e.getCause();
				//message="Server Exception: "+e.getMessage();
				resultCode=2;
			}
		}
		
		JsonParser<String, String> jsonResponse = new JsonParser<String, String>("addNewCaseStatus",resultCode,message,"addNewCaseStatusResult",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/addNewPrivilege", method = RequestMethod.POST)
	public String addNewPrivilege(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("privilege") String privilege){
		
		Byte resultCode=0;
		String message="";
		String result="";
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////////
		log.debug("addNewPrivilege");
		log.debug("Username:"+userName);
		log.debug("privilege:"+privilege);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////////
		if(userName==null && !(boolean)sessionObj.getAttribute("admin"))
			message="Invalid session";
		else{
			
			try {
				if(portalService.insertNewPrivilege(privilege)==null)
					message="Server error";
				else{
					
					message="Success";
					resultCode=1;
				}
			} catch (Exception e) {
				
				e.printStackTrace();
				message="Server Exception: "+e.getCause();
				//message="Server Exception: "+e.getMessage();
				resultCode=2;
			}
		}
		
		JsonParser<String, String> jsonResponse = new JsonParser<String, String>("addNewPrivilege",resultCode,message,"addNewPrivilegeResult",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/addNewRole", method = RequestMethod.POST)
	public String addNewRole(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("roleName") String roleName,
			@RequestParam("roleDesc") String roleDesc/*,
			@RequestParam("privilegeIdJson") String privilegeIdJson*/){
		
		Byte resultCode=0;
		String message="";
		String result="";
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////////
		log.debug("addNewRole");
		log.debug("Username:"+userName);
		log.debug("roleName:"+roleName);
		log.debug("roleDesc:"+roleDesc);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//log.debug("privilegeIdJson:"+privilegeIdJson);
		//////////////////////////
		if(userName==null && !(boolean)sessionObj.getAttribute("admin"))
			message="Invalid session";
		else{
			try {
				
				if(portalService.insertNewRole(roleName, roleDesc/*, privilegeIdList*/)){
					message="Success";
					resultCode=1;
				}
			} catch (Exception e) {
				
				e.printStackTrace();
				message="Server Exception: "+e.getCause();
				//message="Server Exception: "+e.getMessage();
				resultCode=2;
			}
		}
		
		JsonParser<String, String> jsonResponse = new JsonParser<String, String>("addNewRole",resultCode,message,"addNewRoleResult",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/updateLocation", method = RequestMethod.POST)
	public String updateCourt(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("locationId") int locationId,
			@RequestParam("locationName") String locationName,
			@RequestParam("locationDesc") String locationDesc){
		
		Byte resultCode=0;
		String message="";
		String result="";
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////////
		log.debug("updateCourt");
		log.debug("Username:"+userName);
		log.debug("locationId:"+locationId);
		log.debug("loactionName:"+locationName);
		log.debug("locationDesc:"+locationDesc);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////////
		if(userName==null && !(boolean)sessionObj.getAttribute("admin"))
			message="Invalid session";
		else{
			
			try {
				if(!portalService.updateLocation(locationId, locationName, locationDesc))
					message="Server error";
				else{
					
					message="Success";
					resultCode=1;
				}
			} catch (Exception e) {
				
				e.printStackTrace();
				message="Server Exception: "+e.getCause();
				//message="Server Exception: "+e.getMessage();
				resultCode=2;
			}
		}
		
		JsonParser<String, String> jsonResponse = new JsonParser<String, String>("updateLocation",resultCode,message,"updateLocationResult",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/updateCourt", method = RequestMethod.POST)
	public String updateCourt(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("courtId") int courtId,
			@RequestParam("courtName") String courtName,
			@RequestParam("courtDetails") String courtDetails,
			@RequestParam("locationId") int locationId){
		
		Byte resultCode=0;
		String message="";
		String result="";
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////////
		log.debug("updateCourt");
		log.debug("Username:"+userName);
		log.debug("courtId:"+courtId);
		log.debug("courtName:"+courtName);
		log.debug("courtDetails:"+courtDetails);
		log.debug("locationId:"+locationId);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////////
		if(userName==null && !(boolean)sessionObj.getAttribute("admin"))
			message="Invalid session";
		else{
			
			try {
				if(!portalService.updateCourt(courtId, courtName, courtDetails, locationId))
					message="Server error";
				else{
					
					message="Success";
					resultCode=1;
				}
			} catch (Exception e) {
				
				e.printStackTrace();
				message="Server Exception: "+e.getCause();
				//message="Server Exception: "+e.getMessage();
				resultCode=2;
			}
		}
		
		JsonParser<String, String> jsonResponse = new JsonParser<String, String>("updateCourt",resultCode,message,"updateCourtResult",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/updateCaseType", method = RequestMethod.POST)
	public String updateCaseType(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("typeId") int typeId,
			@RequestParam("type") String type,
			@RequestParam("typeDesc") String typeDesc){
		
		Byte resultCode=0;
		String message="";
		String result="";
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////////
		log.debug("updateCaseType");
		log.debug("Username:"+userName);
		log.debug("typeId:"+typeId);
		log.debug("type:"+type);
		log.debug("typeDesc:"+typeDesc);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////////
		if(userName==null && !(boolean)sessionObj.getAttribute("admin"))
			message="Invalid session";
		else{
			
			try {
				if(!portalService.updateCaseType(typeId, type, typeDesc))
					message="Server error";
				else{
					
					message="Success";
					resultCode=1;
				}
			} catch (Exception e) {
				
				e.printStackTrace();
				message="Server Exception: "+e.getCause();
				//message="Server Exception: "+e.getMessage();
				resultCode=2;
			}
		}
		
		JsonParser<String, String> jsonResponse = new JsonParser<String, String>("updateCaseType",resultCode,message,"updateCaseTypeResult",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/updateCaseStatus", method = RequestMethod.POST)
	public String updateCaseStatus(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("statusId") int statusId,
			@RequestParam("status") String status,
			@RequestParam("statusDesc") String statusDesc){
		
		Byte resultCode=0;
		String message="";
		String result="";
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////////
		log.debug("updateCaseStatus");
		log.debug("Username:"+userName);
		log.debug("statusId:"+statusId);
		log.debug("status:"+status);
		log.debug("statusDesc:"+statusDesc);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////////
		if(userName==null && !(boolean)sessionObj.getAttribute("admin"))
			message="Invalid session";
		else{
			
			try {
				if(!portalService.updateCaseStatus(statusId, status, statusDesc))
					message="Server error";
				else{
					
					message="Success";
					resultCode=1;
				}
			} catch (Exception e) {
				
				e.printStackTrace();
				message="Server Exception: "+e.getCause();
				//message="Server Exception: "+e.getMessage();
				resultCode=2;
			}
		}
		
		JsonParser<String, String> jsonResponse = new JsonParser<String, String>("updateCaseStatus",resultCode,message,"updateCaseStatusResult",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/updatePrivilege", method = RequestMethod.POST)
	public String updatePrivilege(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("privilegeId") int privilegeId,
			@RequestParam("privilege") String privilege){
		
		Byte resultCode=0;
		String message="";
		String result="";
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////////
		log.debug("updatePrivilege");
		log.debug("Username:"+userName);
		log.debug("privilegeId:"+privilegeId);
		log.debug("privilege:"+privilege);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////////
		if(userName==null && !(boolean)sessionObj.getAttribute("admin"))
			message="Invalid session";
		else{
			
			try {
				if(!portalService.updatePrivilege(privilegeId, privilege))
					message="Server error";
				else{
					
					message="Success";
					resultCode=1;
				}
			} catch (Exception e) {
				
				e.printStackTrace();
				message="Server Exception: "+e.getCause();
				//message="Server Exception: "+e.getMessage();
				resultCode=2;
			}
		}
		
		JsonParser<String, String> jsonResponse = new JsonParser<String, String>("updatePrivilege",resultCode,message,"updatePrivilegeResult",result);
		return jsonResponse.JsonResponseText();
	}
	
	@RequestMapping(value = "/updateRole", method = RequestMethod.POST)
	public String updateRole(HttpSession sessionObj,HttpServletResponse response,
			@RequestParam("roleId") Integer roleId,
			@RequestParam("roleName") String roleName,
			@RequestParam("roleDesc") String roleDesc){
		
		Byte resultCode=0;
		String message="";
		String result="";
		
		String userName=(String)sessionObj.getAttribute("userName");
		///////////////////////////
		log.debug("updateRole");
		log.debug("Username:"+userName);
		log.debug("roleId:"+roleId);
		log.debug("roleName:"+roleName);
		log.debug("roleDesc:"+roleDesc);
		response.addHeader("Access-Control-Allow-Origin", "*");
		//////////////////////////
		if(userName==null && !(boolean)sessionObj.getAttribute("admin"))
			message="Invalid session";
		else{
			try {				
				if(portalService.updateRole(roleId, roleName, roleDesc/*, privilegeIdList*/)){
					
					message="Success";
					resultCode=1;
				}
				else				
					message="Server error";
			} catch (Exception e) {
				
				e.printStackTrace();
				message="Server Exception: "+e.getCause();
				//message="Server Exception: "+e.getMessage();
				resultCode=2;
			}
			
		}
		
		JsonParser<String, String> jsonResponse = new JsonParser<String, String>("updateRole",resultCode,message,"updateRoleResult",result);
		return jsonResponse.JsonResponseText();
	}

}
