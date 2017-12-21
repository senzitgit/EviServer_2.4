package com.senzit.evidencer.server.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.senzit.evidencer.server.model.PrivateNote;

public class PrivateNoteDaoImp implements PrivateNoteDao {
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Integer insertPrivateNote(PrivateNote noteObj) {
		
		return (Integer)sessionFactory.getCurrentSession().save(noteObj);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getPrivateNoteInfo(int caseEventId) {
		
		String hql="select P.privateNotes,P.createdOn,U.userName,U.firstName from CaseEventDetail E,PrivateNote P, User U"
				+ " where E.caseEventDetailId=:caseEventId and E=P.caseEventDetail and P.createdBy=U";
		return sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("caseEventId", caseEventId)
				.list();
	}

}
