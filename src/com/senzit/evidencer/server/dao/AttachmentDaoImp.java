package com.senzit.evidencer.server.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.senzit.evidencer.server.model.Attachment;

public class AttachmentDaoImp implements AttachmentDao {
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Integer insertAttachment(Attachment obj) {
		
		return (Integer)sessionFactory.getCurrentSession().save(obj);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAttachmentInfo(int caseEventId) {
		
		String hql="select A.fileName,A.attachmentName,A.attachmentDescription,"
				+ "A.createdOn,U.userName,U.firstName from Attachment A,CaseEventDetail E,User U where"
				+ " E.caseEventDetailId=:caseEventId and A.caseEventDetail=E and A.createdBy=U";
		return sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("caseEventId", caseEventId)
				.list();
	}

}
