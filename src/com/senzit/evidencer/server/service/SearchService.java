package com.senzit.evidencer.server.service;

import java.util.Date;
import java.util.List;
import java.util.Properties;

public interface SearchService {
	
	List<Integer> getSittingList(String caseNo);
	List<Integer> getSessionList(String caseNo,int sittingNo);
	List<Integer> getSittingList(String caseNo,Date date);
	List<Integer> getSessionList(String caseNo,int sittingNo,Date date);
	Properties getAdvancedSearchResult(String caseNo,String caseTitle,String caseType,String caseDate);

}
