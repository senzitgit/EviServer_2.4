package com.senzit.evidencer.server.service;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.senzit.evidencer.server.dao.PortalDao;
import com.senzit.evidencer.server.dao.PrivilegeDao;

public class LicenceServiceImp implements LicenceService{
	
	private PrivilegeDao privilegeDao;
	private PortalDao portalDao;

	public void setPrivilegeDao(PrivilegeDao privilegeDao) {
		this.privilegeDao = privilegeDao;
	}

	public void setPortalDao(PortalDao portalDao) {
		this.portalDao = portalDao;
	}

	@Override
	public List<Hashtable<String, Object>> getAllUserPrivileges() {
		
		List<Hashtable<String, Object>> returnList = new ArrayList<Hashtable<String, Object>>();
		
		List<Object[]> allUsers = portalDao.getAllUsers();
		for(Object[] eachUser : allUsers){
			
			List<Object[]> privilegeList = privilegeDao.getUserPrivileges(eachUser[0].toString());
			
			if(privilegeList.isEmpty()) continue;			
			
			Hashtable<String,Object> result = new Hashtable<String,Object>();
			
			result.put("userName", eachUser[0]);
			
			String fullName = eachUser[1].toString();
			/*if(eachUser[2]!=null)
				fullName+=(" "+eachUser[2].toString());*/	// MIDDLE NAME NOT NEEDED
			if(eachUser[3]!=null)
				fullName+=(" "+eachUser[3].toString());
			
			result.put("fullName", fullName);
			
			List<Hashtable<String,Object>> privilegePropList = new ArrayList<Hashtable<String,Object>>();
			for(Object[] eachPrivilege : privilegeList){

				Hashtable<String,Object> privilege = new Hashtable<String,Object>();
				privilege.put("privilege", eachPrivilege[0]);
				privilege.put("dateOfPurchase", eachPrivilege[1]);
				privilege.put("dateOfExpire", eachPrivilege[2]);
				privilegePropList.add(privilege);
			}
			
			result.put("privileges", privilegePropList);
			
			returnList.add(result);
		}
		return returnList;
	}

}
