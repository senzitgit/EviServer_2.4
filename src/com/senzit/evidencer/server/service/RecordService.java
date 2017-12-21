package com.senzit.evidencer.server.service;

import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.json.JSONException;
import org.json.JSONObject;

public interface RecordService {
	
	Object[] checkCase(String caseNo);
	Properties getLastEventDetails(String caseNo);
	Integer createNewCaseEvent(JSONObject newCaseJson) throws JSONException;
	void updateDb(String caseNo,int caseEventId, JSONObject jsonData, String toneAnalyze, String sentimentalAnalyze, String initiatedFrom) throws JSONException;
	//For portal
	List<Hashtable<String,Object>> getLiveSessionDetails();
	Hashtable<String,Object> getLiveCaseDetails(int caseEventId);
}
