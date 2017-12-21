package com.senzit.evidencer.server.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.senzit.evidencer.server.model.Participant;

public class ParticipantDaoImp implements ParticipantDao {
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public int insertParticipant(Participant obj) {
		
		return (Integer)sessionFactory.getCurrentSession().save(obj);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getParticipants(int caseEventId) {
		
		String hql="select P.participantName,P.participantCategory from Participant P,CaseEventDetail E"
				+ " where P.caseEventDetail=E and E.caseEventDetailId=:caseEventId";
		return sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("caseEventId", caseEventId)
				.list();
	}

}
