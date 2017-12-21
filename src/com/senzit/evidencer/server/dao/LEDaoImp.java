package com.senzit.evidencer.server.dao;

import org.hibernate.SessionFactory;

import com.senzit.evidencer.server.model.LandEPath;

public class LEDaoImp implements LEDao {
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public String insertLocation(LandEPath obj) {
		
		System.out.println(obj.getUser().getUserName());
		return (String) sessionFactory.getCurrentSession().save(obj);
	}

	@Override
	public String getLocation(String userName) {
		
		String hql = "select lePath from LandEPath where userName = :userName";
		return (String) sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("userName", userName)
				.uniqueResult();
	}

	@Override
	public int updateLocation(String userName, String loc) {
		
		String hql = "update LandEPath set lePath=:path where userName=:userName";
		return (int) sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("path", loc)
				.setParameter("userName", userName)
				.executeUpdate();
	}

}
