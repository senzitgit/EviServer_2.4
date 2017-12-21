package com.senzit.evidencer.server.dao;

import java.util.List;

import com.senzit.evidencer.server.model.UserPrivilege;

public interface PrivilegeDao {
	
	List<Object[]> getUserPrivileges(String userName);
	List<String> getAllPrivileges();
	
	long checkPrivateNotePrivilege(String userName);
	Integer insertUserPrivilege(UserPrivilege obj);

}
