package com.senzit.evidencer.server.service;

import java.util.Hashtable;
import java.util.List;

public interface PortalService {
	
	List<Hashtable<String,Object>> getLocationList();
	List<Hashtable<String,Object>> getCaseTypeList();
	List<Hashtable<String,Object>> getRoleList();
	List<Hashtable<String,Object>> getCourtList();
	List<Hashtable<String,Object>> getCaseStatusList();
	List<Hashtable<String,Object>> getPrivilegeList();
	
	Integer insertNewCourt(String courtName,String courtDetails,int locId);
	Integer insertNewLocation(String locName,String locDesc);
	Integer insertNewCaseType(String type,String typeDesc);
	Integer insertNewCaseStatus(String status,String desc);
	Integer insertNewPrivilege(String privilege);
	boolean insertNewRole(String roleName,String roleDesc/*,List<Integer> privilegeIdArray*/);
	
	boolean updateCourt(int courtId,String courtName,String courtDetails,int locId);
	boolean updateLocation(int locId,String locName,String locDesc);
	boolean updateCaseType(int typeId,String type,String typeDesc);
	boolean updateCaseStatus(int statusId,String status,String desc);
	boolean updatePrivilege(int privilegeId,String privilege);
	boolean updateRole(int roleId,String roleName,String roleDesc/*,List<Integer> privilegeIdArray*/);

}
