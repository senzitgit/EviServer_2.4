package com.senzit.evidencer.server.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.senzit.evidencer.server.model.Role;
import com.senzit.evidencer.server.model.User;

public class UserDaoImp implements UserDao {
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Object[] getUserNames(String userName) {
		
		String hql="select firstName,middleName,lastName,userType from User"
				+ " where userName=:userName";
		return (Object[]) sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("userName", userName)
				.uniqueResult();
	}

	@Override
	public String getUserRoleName(String userName) {
		
		String hql="select R.roleName from User U,Role R where U.userRole=R and U.userName=:userName";
		return (String)sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("userName", userName)
				.uniqueResult();
	}

	@Override
	public String getPrimaryEmailId(String userName) {
		
		return (String) sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.add(Restrictions.eq("userName", userName))
				.setProjection(Projections.property("primaryEmailId"))
				.uniqueResult();
	}

	@Override
	public int updatePassword(String userName, String newPassword) {
		
		return sessionFactory.getCurrentSession()
				.createQuery("update User set password=:newPassword where userName=:userName")
				.setParameter("newPassword",newPassword)
				.setParameter("userName",userName)
				.executeUpdate();//returns no of rows affected
	}

	@Override
	public String getPassword(String userName) {
		
		return (String)sessionFactory.getCurrentSession().createCriteria(User.class)
				.add(Restrictions.eq("userName", userName))
				.setProjection(Projections.property("password"))
				.uniqueResult();
	}

	@Override
	public long checkUserNameEmail(String userName, String emailId) {
		
		String hql="select count(*) from User where userName=:userName or primaryEmailId=:emailId";
		return (long)sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("userName", userName)
				.setParameter("emailId", emailId)
				.uniqueResult();
	}

	@Override
	public String insertNewUser(User obj) {
		
		return (String) sessionFactory.getCurrentSession().save(obj);
	}

	@Override
	public Object[] getUserPrimaryDetails(String userName) {
		
		String hql="select firstName,primaryEmailId,primaryMobileNumber from User"
				+ " where userName=:userName";
		return (Object[]) sessionFactory.getCurrentSession()
				.createQuery(hql)
				.setParameter("userName", userName)
				.uniqueResult();
	}

	@Override
	public String getAdminEmail() {
		
		byte type=0;
		return (String) sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.add(Restrictions.eq("userType",type))
				.setProjection(Projections.property("primaryEmailId"))
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getPartialRegisters() {
		
		String hql="select userName,firstName,middleName,lastName,primaryEmailId,primaryMobileNumber from User"
				+ " where userRole is null";
		return sessionFactory.getCurrentSession().createQuery(hql)
				.list();
	}

	@Override
	public int setRoleType(String userName, Byte type, int roleId) {
		
		Role role=new Role();
		role.setRoleId(roleId);
		String hql="update User set userType=:type,userRole=:role where userName=:userName";
		return sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("type", type)
				.setParameter("role", role)
				.setParameter("userName", userName)
				.executeUpdate();
	}

	@Override
	public void removeUser(String userName) {
		
		User user=new User();
		user.setUserName(userName);
		sessionFactory.getCurrentSession().delete(user);
		
	}

	@Override
	public User getUser(String userName) {
		
		return (User)sessionFactory.getCurrentSession().get(User.class, userName);
	}

	@Override
	public void updateUser(User user) {
		
		sessionFactory.getCurrentSession().update(user);
	}

}
