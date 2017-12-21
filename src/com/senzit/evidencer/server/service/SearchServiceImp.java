package com.senzit.evidencer.server.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.senzit.evidencer.server.dao.CaseDao;
import com.senzit.evidencer.server.model.CaseInfo;
import com.senzit.evidencer.server.model.CaseType;

public class SearchServiceImp implements SearchService {

	private CaseDao caseDao;

	public void setCaseDao(CaseDao caseDao) {
		this.caseDao = caseDao;
	}

	@Override
	public List<Integer> getSittingList(String caseNo) {

		return caseDao.getSittingNumbers(caseNo);
	}

	@Override
	public List<Integer> getSessionList(String caseNo, int sittingNo) {

		return caseDao.getSessionNumbers(caseNo,sittingNo);
	}

	@Override
	public List<Integer> getSittingList(String caseNo,Date date) {

		return caseDao.getSittingNumbers(caseNo,date);
	}

	@Override
	public List<Integer> getSessionList(String caseNo, int sittingNo,Date date) {

		return caseDao.getSessionNumbers(caseNo,sittingNo,date);
	}

	@Override
	public Properties getAdvancedSearchResult(String caseNo, String caseTitle,
			String caseType, String caseDate) {
		
		
		boolean caseNoB=(caseNo.isEmpty())?false:true;
		boolean caseTitleB=(caseTitle.isEmpty())?false:true;
		boolean caseTypeB=(caseType.isEmpty())?false:true;
		boolean caseDateB=(caseDate.isEmpty())?false:true;

		String hql="";
		
		CaseType caseTypeObj=new CaseType();
		if(caseTypeB) caseTypeObj.setCaseTypeId(caseDao.getCaseTypeId(caseType));
		
		ArrayList<Properties> casePropList=new ArrayList<Properties>();
		List<Integer> sittingList=new ArrayList<Integer>();
		Properties resultProp=new Properties();

		if(caseDateB){
			
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date=null;
			try {
				date= formatter.parse(caseDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}


			List<CaseInfo> caseObjList=caseDao.getCaseFromDate(date);
			
			int caseObjListSize=caseObjList.size();
			if(caseObjListSize==0)
				return resultProp;

			for(int i=0;i<caseObjListSize;i++){
				
				
				CaseInfo caseObj=caseObjList.get(i);
				Boolean flag=true;
				if(caseNoB && !caseObj.getCaseNo().equals(caseNo))
					flag=false;
				else if(caseTitleB && !caseObj.getCaseTitle().toLowerCase().contains(caseTitle.toLowerCase()))
					flag=false;
				else if(caseTypeB && caseObj.getCaseType()!=caseTypeObj)
					flag=false;
				
				if(flag&&!caseNoB){

					Properties caseProp=new Properties();
					caseProp.put("caseNo", caseObj.getCaseNo());
					caseProp.put("caseTitle", caseObj.getCaseTitle());
					caseProp.put("caseDescription", caseObj.getCaseDescription());
					casePropList.add(caseProp);

				}
				
			}
			if(caseNoB)
				sittingList=caseDao.getSittingNumbers(caseNo, date);

			resultProp.put("CaseList", casePropList);
			resultProp.put("SittingList", sittingList);


		}

		else{

			hql="SELECT caseNo,caseTitle,caseDescription FROM CaseInfo";
			ArrayList<Object> paramList=new ArrayList<Object>();

			if(caseNoB||caseTitleB||caseTypeB||caseDateB==true){

				hql+=" WHERE";

				if(caseNoB){
					hql+=" caseNo=:caseNo";
					paramList.add("caseNo");
					paramList.add(caseNo);
				}
				if(caseTitleB){
					if(caseNoB) hql+=" AND";
					hql+=" lower(caseTitle) like :caseTitle";
					paramList.add("caseTitle");
					paramList.add("%"+caseTitle.toLowerCase()+"%");
				}
				if(caseTypeB){
					if(caseNoB||caseTitleB) hql+=" AND";
					hql+=" caseType=:caseType";
					paramList.add("caseType");
					paramList.add(caseTypeObj);
				}
			}			
			
			List<Object[]> caseList=caseDao.getCaseInfo(hql, paramList);

			int listSize=caseList.size();
			if(listSize==0)
				return resultProp;
			
			if(!caseNoB)
				for(int i=0;i<listSize;i++){
					Properties caseProp=new Properties();

					Object[] obj=caseList.get(i);
					caseProp.put("caseNo", obj[0]);
					caseProp.put("caseTitle", obj[1]);
					caseProp.put("caseDescription", obj[2]);
					casePropList.add(caseProp);
				}
			else{
				sittingList=caseDao.getSittingNumbers(caseNo);

			}
			resultProp.put("CaseList", casePropList);
			resultProp.put("SittingList", sittingList);
		}
		
		return resultProp;
	}

}
