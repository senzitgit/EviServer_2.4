package com.senzit.evidencer.server.dao;

import java.util.List;

import com.senzit.evidencer.server.model.SecurityQuestion;

public interface SecurityQuestionDao {
	
	String insertNew(SecurityQuestion qnObj);
	void deleteQn(String userName);
	List<Object[]> getSecurityQns(String userName);
	SecurityQuestion getSecurityQuestionObj(String userName);

}
