package com.senzit.evidencer.server.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.senzit.evidencer.server.model.CaseStatus;
import com.senzit.evidencer.server.model.CaseType;
import com.senzit.evidencer.server.model.Court;
import com.senzit.evidencer.server.model.Location;
import com.senzit.evidencer.server.model.Privilege;
import com.senzit.evidencer.server.model.Role;

public class PortalDaoImp implements PortalDao{
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Location> getAllLocations() {
		
		return sessionFactory.getCurrentSession().createCriteria(Location.class).list();
	}

	@Override
	public Integer insertNewLocation(Location loc) {
		
		return (Integer) sessionFactory.getCurrentSession().save(loc);
	}

	@Override
	public void updateLocation(Location loc) {
		
		sessionFactory.getCurrentSession().update(loc);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CaseType> getAllCaseTypes() {
		
		return sessionFactory.getCurrentSession().createCriteria(CaseType.class).list();
	}

	@Override
	public Integer insertNewCaseType(CaseType typeObj) {
		
		return (Integer) sessionFactory.getCurrentSession().save(typeObj);
	}

	@Override
	public void updateCaseType(CaseType typeObj) {
		
		sessionFactory.getCurrentSession().update(typeObj);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getAllRoles() {
		
		return sessionFactory.getCurrentSession().createCriteria(Role.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAllCourts() {
		
		String hql="select C,L.locationName from Court C,Location L where C.location=L";
		return sessionFactory.getCurrentSession().createQuery(hql)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CaseStatus> getAllCaseStatuses() {
		
		return sessionFactory.getCurrentSession().createCriteria(CaseStatus.class).list();
	}

	@Override
	public Integer insertNewCourt(Court courtObj) {
		
		return (Integer)sessionFactory.getCurrentSession().save(courtObj);
	}

	@Override
	public void updateCourt(Court courtObj) {
		
		sessionFactory.getCurrentSession().update(courtObj);
	}

	@Override
	public Integer insertNewCaseStatus(CaseStatus statusObj) {
		
		return (Integer)sessionFactory.getCurrentSession().save(statusObj);
	}

	@Override
	public void updateCaseStatus(CaseStatus statusObj) {
		
		sessionFactory.getCurrentSession().update(statusObj);		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Privilege> getAllPrivileges() {
		
		return sessionFactory.getCurrentSession().createCriteria(Privilege.class).list();
	}

	@Override
	public Integer insertNewPrivilege(Privilege obj) {
		
		return (Integer)sessionFactory.getCurrentSession().save(obj);
	}

	@Override
	public void updatePrivilege(Privilege obj) {
		
		sessionFactory.getCurrentSession().update(obj);
	}

	@Override
	public Integer insertNewRole(Role roleObj) {
		
		return (Integer)sessionFactory.getCurrentSession().save(roleObj);
	}

	@Override
	public void updateRole(Role roleObj) {
		
		sessionFactory.getCurrentSession().update(roleObj);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAllUsers() {
		
		String hql = "select userName,firstName,middleName,lastName from User";
		return (List<Object[]>)sessionFactory.getCurrentSession().createQuery(hql).list();
	}

	/*@Override
	public int deleteAllPrivilegeOfRole(Role roleObj) {
		
		String hql="delete from RolePrivilege where role=:role";
		return sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("role", roleObj)
				.executeUpdate();
		
	}*/

}
