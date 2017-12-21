package com.senzit.evidencer.server.dao;

import java.util.List;

import com.senzit.evidencer.server.model.User;

public interface UserDao {
	
	Object[] getUserNames(String userName);
	String getUserRoleName(String userName);
	String getPrimaryEmailId(String userName);
	String getPassword(String userName);
	long checkUserNameEmail(String userName, String emailId);	
	int updatePassword(String userName, String newPassword);
	
	String insertNewUser(User obj);
	Object[] getUserPrimaryDetails(String userName);
	String getAdminEmail();
	
	List<Object[]> getPartialRegisters();
	int setRoleType(String userName,Byte type,int roleId);
	void removeUser(String userName);
	
	User getUser(String userName);
	void updateUser(User user);

}
