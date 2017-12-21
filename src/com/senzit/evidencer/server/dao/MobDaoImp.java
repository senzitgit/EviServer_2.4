package com.senzit.evidencer.server.dao;

import java.util.List;

import com.senzit.evidencer.server.model.MobData;
import com.senzit.evidencer.server.model.MobFile;
import com.senzit.evidencer.server.model.MobUser;

import org.hibernate.SessionFactory;

public class MobDaoImp implements MobDao {
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public String insertUser(MobUser obj) {
		
		return (String) sessionFactory.getCurrentSession().save(obj);
	}

	@Override
	public long checkUserEmailId(String email) {
		
		String hql = "select count(*) from MobUser where email=:email";
		return (long) sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("email", email)
				.uniqueResult();
	}

	@Override
	public MobUser getUser(String userName) {
		
		return (MobUser) sessionFactory.getCurrentSession().get(MobUser.class, userName);
	}

	@Override
	public Integer insertMobFile(MobFile file) {
		
		return (Integer) sessionFactory.getCurrentSession().save(file);
	}
	
	@Override
	public String insertMobData(MobData file) {
		
		return (String) sessionFactory.getCurrentSession().save(file);
	}

	@Override
	public long checkRandomCodeExists(String randomCode) {
		
		String hql = "select count(*) from MobData where randomCode=:randomCode";
		return (long) sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("randomCode", randomCode)
				.uniqueResult();
	}

	@Override
	public MobData getMobData(String randomCode) {
		
		String hql = "from MobData where randomCode=:randomCode";
		return (MobData) sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("randomCode", randomCode)
				.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MobFile> getMobFiles(MobData mobData) {
		
		String hql = "from MobFile where mobData=:mobData";
		return (List<MobFile>) sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("mobData", mobData)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MobData> getAllMobData() {
		
		String hql = "from MobData order by createdOn desc";
		return sessionFactory.getCurrentSession().createQuery(hql).list();
	}

	@Override
	public long authenticate(String email, String password) {
		
		String hql = "select count(*) from MobUser where email=:email and password=:password";
		return (long) sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("email", email)
				.setParameter("password", password)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getUserMobDetail(String userName) {
		
		String hql = "select F.randomCode, F.status, F.createdOn from MobData F,MobUser U where"
				+ " U.email=:userName and U=F.createdBy order by F.createdOn desc";
		return sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("userName", userName)
				.list();
	}

	@Override
	public void updatePassword(String email,String newPassword) {
		
		String hql = "update MobUser set password=:password where email=:email";
		sessionFactory.getCurrentSession().createQuery(hql)
		.setParameter("email", email)
		.setParameter("password", newPassword)
		.executeUpdate();
	}

	@Override
	public int setMobFileStatus(String randomCode, String status) {
		
		String hql = "update MobData set status=:status where randomCode=:randomCode";
		return sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("status", status)
				.setParameter("randomCode", randomCode)
				.executeUpdate();
	}

}
