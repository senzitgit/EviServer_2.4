package com.senzit.evidencer.server.dao;

import java.util.List;

import com.senzit.evidencer.server.model.PrivateNote;

public interface PrivateNoteDao {
	
	Integer insertPrivateNote(PrivateNote noteObj);
	List<Object[]> getPrivateNoteInfo(int caseEventId);

}
