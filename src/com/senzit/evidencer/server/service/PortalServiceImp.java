package com.senzit.evidencer.server.service;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.senzit.evidencer.server.dao.PortalDao;
import com.senzit.evidencer.server.model.CaseStatus;
import com.senzit.evidencer.server.model.CaseType;
import com.senzit.evidencer.server.model.Court;
import com.senzit.evidencer.server.model.Location;
import com.senzit.evidencer.server.model.Privilege;
import com.senzit.evidencer.server.model.Role;

public class PortalServiceImp implements PortalService{
	
	private PortalDao portalDao;
	
	public void setPortalDao(PortalDao portalDao) {
		this.portalDao = portalDao;
	}

	@Override
	public List<Hashtable<String, Object>> getLocationList() {
		
		List<Location> locObjList=portalDao.getAllLocations();
		
		List<Hashtable<String, Object>> result=new ArrayList<Hashtable<String, Object>>();
		
		for(Location obj:locObjList){
			
			Hashtable<String, Object> temp=new Hashtable<String, Object>();
			temp.put("locId", obj.getLocationId());
			temp.put("locName", obj.getLocationName());
			temp.put("locDesc", obj.getLocationDescription());
			
			result.add(temp);
		}
		return result;
	}

	@Override
	public List<Hashtable<String, Object>> getCaseTypeList() {
		
		List<CaseType> caseTypeObjList=portalDao.getAllCaseTypes();
		
		List<Hashtable<String, Object>> result=new ArrayList<Hashtable<String, Object>>();
		
		for(CaseType obj:caseTypeObjList){
			
			Hashtable<String, Object> temp=new Hashtable<String, Object>();
			temp.put("caseTypeId", obj.getCaseTypeId());
			temp.put("caseType", obj.getCaseType());
			temp.put("caseTypeDesc", obj.getTypeDescription());
			
			result.add(temp);
		}
		return result;
	}

	@Override
	public List<Hashtable<String, Object>> getRoleList() {
		
		List<Role> roleObjList=portalDao.getAllRoles();
		
		List<Hashtable<String, Object>> result=new ArrayList<Hashtable<String, Object>>();
		
		for(Role obj:roleObjList){
			
			Hashtable<String, Object> temp=new Hashtable<String, Object>();
			temp.put("roleId", obj.getRoleId());
			temp.put("roleName", obj.getRoleName());
			temp.put("roleDesc", obj.getRoleDescription());
			
			result.add(temp);
		}
		return result;
	}

	@Override
	public List<Hashtable<String, Object>> getCourtList() {
		
		List<Object[]> courtObjList=portalDao.getAllCourts();
		
		List<Hashtable<String, Object>> result=new ArrayList<Hashtable<String, Object>>();
		
		for(Object[] obj:courtObjList){
			
			Hashtable<String, Object> temp=new Hashtable<String, Object>();
			temp.put("courtId", ((Court)obj[0]).getCourtId());
			temp.put("courtName", ((Court)obj[0]).getCourtName());
			temp.put("courtDetails", ((Court)obj[0]).getCourtDetails());
			temp.put("location", obj[1]);
			
			result.add(temp);
		}
		return result;
	}

	@Override
	public List<Hashtable<String, Object>> getCaseStatusList() {
		
		List<CaseStatus> statusObjList=portalDao.getAllCaseStatuses();
		
		List<Hashtable<String, Object>> result=new ArrayList<Hashtable<String, Object>>();
		
		for(CaseStatus obj:statusObjList){
			
			Hashtable<String, Object> temp=new Hashtable<String, Object>();
			temp.put("statusId", obj.getCaseStatusId());
			temp.put("status", obj.getStatus());
			temp.put("statusDesc", obj.getStatusDescription());
			
			result.add(temp);
		}
		return result;
	}

	@Override
	public Integer insertNewCourt(String courtName, String courtDetails,
			int locId) {
		
		Location loc=new Location();
		loc.setLocationId(locId);
		Court court=new Court();
		court.setCourtName(courtName);
		court.setCourtDetails(courtDetails);
		court.setLocation(loc);
		return portalDao.insertNewCourt(court);
	}

	@Override
	public Integer insertNewLocation(String locName, String locDesc) {
		
		Location loc=new Location();
		loc.setLocationName(locName);
		loc.setLocationDescription(locDesc);
		return portalDao.insertNewLocation(loc);
	}

	@Override
	public Integer insertNewCaseType(String type, String typeDesc) {
		
		CaseType typeObj=new CaseType();
		typeObj.setCaseType(type);
		typeObj.setTypeDescription(typeDesc);
		return portalDao.insertNewCaseType(typeObj);
	}

	@Override
	public Integer insertNewCaseStatus(String status, String desc) {
		
		CaseStatus statusObj=new CaseStatus();
		statusObj.setStatus(status);
		statusObj.setStatusDescription(desc);
		return portalDao.insertNewCaseStatus(statusObj);
	}

	@Override
	public boolean updateCourt(int courtId, String courtName,
			String courtDetails, int locId) {
		
		Location loc=new Location();
		loc.setLocationId(locId);
		Court court=new Court();
		court.setCourtId(courtId);
		court.setCourtName(courtName);
		court.setCourtDetails(courtDetails);
		court.setLocation(loc);
		portalDao.updateCourt(court);
		return true;	// FOR FUTURE USE
	}

	@Override
	public boolean updateLocation(int locId, String locName, String locDesc) {
		
		Location loc=new Location();
		loc.setLocationId(locId);
		loc.setLocationName(locName);
		loc.setLocationDescription(locDesc);
		portalDao.updateLocation(loc);
		return true;	//FOR FUTURE USE
	}

	@Override
	public boolean updateCaseType(int typeId, String type, String typeDesc) {
		
		CaseType typeObj=new CaseType();
		typeObj.setCaseTypeId(typeId);
		typeObj.setCaseType(type);
		typeObj.setTypeDescription(typeDesc);
		portalDao.updateCaseType(typeObj);
		return true;	//FOR FUTURE USE
	}

	@Override
	public boolean updateCaseStatus(int statusId, String status, String desc) {
		
		CaseStatus statusObj=new CaseStatus();
		statusObj.setStatus(status);
		statusObj.setStatusDescription(desc);
		statusObj.setCaseStatusId(statusId);
		portalDao.updateCaseStatus(statusObj);
		return true;	//FOR FUTURE USE
	}

	@Override
	public List<Hashtable<String, Object>> getPrivilegeList() {
		
		List<Privilege> privilegeObjList=portalDao.getAllPrivileges();
		
		List<Hashtable<String, Object>> result=new ArrayList<Hashtable<String, Object>>();
		
		for(Privilege obj:privilegeObjList){
			
			Hashtable<String, Object> temp=new Hashtable<String, Object>();
			temp.put("privilegeId", obj.getPrivilegeId());
			temp.put("privilege", obj.getPrivilege());
			
			result.add(temp);
		}
		return result;
	}

	@Override
	public Integer insertNewPrivilege(String privilege) {
		
		Privilege prObj=new Privilege();
		prObj.setPrivilege(privilege);
		return portalDao.insertNewPrivilege(prObj);
	}

	@Override
	public boolean updatePrivilege(int privilegeId, String privilege) {
		
		Privilege prObj=new Privilege();
		prObj.setPrivilegeId(privilegeId);
		prObj.setPrivilege(privilege);
		portalDao.updatePrivilege(prObj);
		return true;	//FOR FUTURE USE
	}

	@Override
	public boolean insertNewRole(String roleName, String roleDesc) {
		
		Role role=new Role();
		role.setRoleName(roleName);
		role.setRoleDescription(roleDesc);
		boolean flag=((portalDao.insertNewRole(role))==null)?false:true;
		return flag;
	}

	@Override
	public boolean updateRole(int roleId, String roleName, String roleDesc) {
		
		Role role=new Role();
		role.setRoleName(roleName);
		role.setRoleDescription(roleDesc);
		role.setRoleId(roleId);

		portalDao.updateRole(role);
				
		return true;	//FOR FUTURE USE
	}

}
