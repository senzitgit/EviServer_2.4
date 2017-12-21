package com.senzit.evidencer.server.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.senzit.evidencer.server.model.SecurityQuestion;

public class SecurityQuestionDaoImp implements SecurityQuestionDao {
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public String insertNew(SecurityQuestion qnObj) {
		
		return (String)sessionFactory.getCurrentSession().save(qnObj);
	}

	@Override
	public void deleteQn(String userName) {
		
		SecurityQuestion qnObj=new SecurityQuestion();
		qnObj.setUserName(userName);
		sessionFactory.getCurrentSession().delete(qnObj);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getSecurityQns(String userName) {
		
		return sessionFactory.getCurrentSession()
				.createQuery("select S.securityQuestion1,S.securityQuestion2,S.securityQuestion3"
						+" from SecurityQuestion S where S.userName=:userName")
				.setParameter("userName", userName)
				.list();
	}

	@Override
	public SecurityQuestion getSecurityQuestionObj(String userName) {
		
		return (SecurityQuestion)sessionFactory.getCurrentSession().get(SecurityQuestion.class, userName);
	}

}
