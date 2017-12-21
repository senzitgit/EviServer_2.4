package com.senzit.evidencer.server.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;

import com.senzit.evidencer.server.model.Privilege;
import com.senzit.evidencer.server.model.UserPrivilege;

public class PrivilegeDaoImp implements PrivilegeDao {
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getUserPrivileges(String userName) {
		
		String hql="select P.privilege,UP.dateOfPurchase,UP.dateOfExpire from User U,UserPrivilege UP,Privilege P"
				+ " where U.userName=:userName and U=UP.user and UP.privilege=P";
		
		return (List<Object[]>)sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("userName", userName)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllPrivileges() {
		
		return (List<String>) sessionFactory.getCurrentSession().createCriteria(Privilege.class)
				.setProjection(Projections.property("privilege"))
				.list();
	}

	@Override
	public long checkPrivateNotePrivilege(String userName) {
		
		String hql="select count(*) from User U,UserPrivilege UP,Privilege P"
				+ " where U.userName=:userName and U=UP.user and UP.privilege=P"
				+ " and P.privilege=:privateNoteString";
		return (long)sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("userName", userName)
				.setParameter("privateNoteString", "PrivateNote")
				.uniqueResult();
	}
	
	@Override
	public Integer insertUserPrivilege(UserPrivilege obj) {
		
		return (Integer)sessionFactory.getCurrentSession().save(obj);
	}

}
