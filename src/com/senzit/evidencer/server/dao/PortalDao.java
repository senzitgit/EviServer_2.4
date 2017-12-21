package com.senzit.evidencer.server.dao;

import java.util.List;

import com.senzit.evidencer.server.model.CaseStatus;
import com.senzit.evidencer.server.model.CaseType;
import com.senzit.evidencer.server.model.Court;
import com.senzit.evidencer.server.model.Location;
import com.senzit.evidencer.server.model.Privilege;
import com.senzit.evidencer.server.model.Role;

public interface PortalDao {
	
	List<Location> getAllLocations();
	Integer insertNewLocation(Location loc);
	void updateLocation(Location loc);
	
	List<CaseType> getAllCaseTypes();
	Integer insertNewCaseType(CaseType typeObj);
	void updateCaseType(CaseType typeObj);
	
	List<Object[]> getAllCourts();
	Integer insertNewCourt(Court courtObj);
	void updateCourt(Court courtObj);
	
	List<Role> getAllRoles();
	Integer insertNewRole(Role roleObj);
	void updateRole(Role roleObj);
	
	List<CaseStatus> getAllCaseStatuses();
	Integer insertNewCaseStatus(CaseStatus statusObj);
	void updateCaseStatus(CaseStatus statusObj);	

	List<Privilege> getAllPrivileges();
	Integer insertNewPrivilege(Privilege obj);
	void updatePrivilege(Privilege obj);
	
	List<Object[]> getAllUsers();
	//int deleteAllPrivilegeOfRole(Role roleObj);

}
