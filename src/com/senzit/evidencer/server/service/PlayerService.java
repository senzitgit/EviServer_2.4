package com.senzit.evidencer.server.service;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.json.JSONException;

public interface PlayerService {
	
	HashMap<String,Object> getCaseEventDetails(String caseNo,int sittingNo,int sessionNo);
	List<Hashtable<String, Object>> getAttachmentDetails(int caseEventId,String userName) throws JSONException;
	List<Hashtable<String, Object>> getPrivateNoteDetails(int caseEventId);

}
