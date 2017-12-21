package com.senzit.evidencer.server.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.senzit.evidencer.server.model.CaseEventDetail;
import com.senzit.evidencer.server.model.CaseInfo;
import com.senzit.evidencer.server.model.CaseStatus;
import com.senzit.evidencer.server.model.CaseType;

public class CaseDaoImp implements CaseDao {
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public CaseInfo getCaseInfoObj(String caseNo) {
		
		return (CaseInfo)sessionFactory.getCurrentSession().get(CaseInfo.class, caseNo);
	}

	@Override
	public String getCaseStatus(String caseNo) {
		
		String hql="select S.status from CaseInfo C,CaseStatus S where C.caseNo=:caseNo and C.caseStatus=S";
		return (String)sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("caseNo", caseNo)
				.uniqueResult();
	}

	@Override
	public Object[] getLatEventDetails(String caseNo) {
		
		String hql="select C.caseTitle,C.caseDescription,T.caseType, E.sittingNo, E.sessionNo"
					+ " from CaseEventDetail E, CaseInfo C, CaseType T"
					+ " where C.caseNo=:caseNo and E.caseId=C and C.caseType=T order by E.startTime desc";
		return (Object[]) sessionFactory.getCurrentSession()				
				.createQuery(hql)// LIMIT 1
				.setParameter("caseNo", caseNo)
				.setMaxResults(1)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CaseType> getCaseTypeObj(String type) {
		
		return (List<CaseType>)sessionFactory.getCurrentSession().createCriteria(CaseType.class)
				.add(Restrictions.eq("caseType", type))
				.list();
				
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CaseStatus> getCaseStatusObj(String status) {
		
		return (List<CaseStatus>)sessionFactory.getCurrentSession().createCriteria(CaseStatus.class)
				.add(Restrictions.eq("status", status))
				.list();
	}

	@Override
	public String insertNewCase(CaseInfo caseObj) {
		
		return (String)sessionFactory.getCurrentSession().save(caseObj);
	}

	@Override
	public Integer insertNewCaseEvent(CaseEventDetail caseEventObj) {
		
		return (Integer)sessionFactory.getCurrentSession().save(caseEventObj);
	}

	@Override
	public CaseEventDetail getCaseEventDetailObj(int caseEventId) {
		
		return (CaseEventDetail)sessionFactory.getCurrentSession().get(CaseEventDetail.class, caseEventId);
	}

	@Override
	public void updateCaseEventDetailObj(CaseEventDetail obj) {
		
		sessionFactory.getCurrentSession().update(obj);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CaseInfo> getLiveCaseObj() {
		
		String hql="select distinct caseId from CaseEventDetail where endTime is null";
		return sessionFactory.getCurrentSession().createQuery(hql)
				.list();
	}

	@Override
	public Object[] getLastEventDetail(CaseInfo caseObj) {
		
		String hql="select C,E.caseEventDetailId,E.sittingNo,E.sessionNo,E.startTime,CT.courtName"
				+ " from CaseInfo C,CaseEventDetail E,Court CT"
				+ " where C=:caseObj and E.caseId=C and E.endTime is null and CT=E.court order by E.startTime desc";
		return (Object[])sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("caseObj", caseObj)
				.setMaxResults(1)
				.uniqueResult();
	}
	
	@Override
	public Object[] getCaseDetail(int caseEventId) {
		
		String hql="select C,E.sittingNo,E.sessionNo,E.startTime from CaseInfo C,CaseEventDetail E where E.caseId=C and E.caseEventDetailId=:caseEventId";
		return (Object[])sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("caseEventId", caseEventId)
				.uniqueResult();
	}

	@Override
	public Integer setAttachmentFlag(int caseEventDetailId) {
		
		
		String hql="update CaseEventDetail set attachmentFlag=:flag where caseEventDetailId=:caseEventId";
		return sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("flag", true)
				.setParameter("caseEventId", caseEventDetailId)
				.executeUpdate();
	}

	@Override
	public Integer setPrivateNoteFlag(int caseEventDetailId) {
		
		String hql="update CaseEventDetail set privateNoteFlag=:flag where caseEventDetailId=:caseEventId";
		return sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("flag", true)
				.setParameter("caseEventId", caseEventDetailId)
				.executeUpdate();
	}
	
	
	//Search & Player
	


	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getSittingNumbers(String caseNo) {
		
		String hql="select distinct E.sittingNo from CaseInfo C, CaseEventDetail E where"
				+ " C.caseNo=:caseNo and E.caseId=C";
		return (List<Integer>)sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("caseNo", caseNo)
				.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getSessionNumbers(String caseNo,int sittingNo) {
		
		String hql="select distinct E.sessionNo from CaseInfo C, CaseEventDetail E where"
				+ " C.caseNo=:caseNo and E.caseId=C and E.sittingNo=:sittingNo";
		return (List<Integer>)sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("caseNo", caseNo)
				.setParameter("sittingNo", sittingNo)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CaseInfo> getCaseFromDate(Date date) {
		
		String hql="select distinct C from CaseInfo C,CaseEventDetail E where E.eventDate=:date"
				+ " and C=E.caseId";
		return (List<CaseInfo>)sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("date", date)
				.list();
	}

	@Override
	public Integer getCaseTypeId(String caseType) {
		
		return (Integer)sessionFactory.getCurrentSession().createCriteria(CaseType.class)
				.add(Restrictions.eq("caseType", caseType))
				.setProjection(Projections.property("caseTypeId"))
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getSittingNumbers(String caseNo, Date date) {
		
		String hql="select distinct E.sittingNo from CaseInfo C, CaseEventDetail E where"
				+ " C.caseNo=:caseNo and E.caseId=C and E.eventDate=:date";
		return (List<Integer>)sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("caseNo", caseNo)
				.setParameter("date", date)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getSessionNumbers(String caseNo, int sittingNo,
			Date date) {
		
		String hql="select distinct E.sessionNo from CaseInfo C, CaseEventDetail E where"
				+ " C.caseNo=:caseNo and E.caseId=C and E.sittingNo=:sittingNo and E.eventDate=:date";
		return (List<Integer>)sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("caseNo", caseNo)
				.setParameter("sittingNo",sittingNo)
				.setParameter("date", date)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getCaseInfo(String hql, List<Object> paramList) {
		
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		int listSize=paramList.size();
		
		for(int i=0;i<listSize;i+=2){
			query.setParameter((String)paramList.get(i), paramList.get(i+1));
		}
		return (List<Object[]>)query.list();
	}	

	@Override
	public Object[] getFullCaseDetails(String caseNo, int sittingNo,
			int sessionNo) {
		
		String hql="select C,E from CaseInfo C,CaseEventDetail E where"
				+ " C.caseNo=:caseNo and C=E.caseId and E.sittingNo=:sittingNo and E.sessionNo=:sessionNo";
		return (Object[])sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("caseNo", caseNo)
				.setParameter("sittingNo", sittingNo)
				.setParameter("sessionNo", sessionNo)
				.uniqueResult();
				
	}
	

}
