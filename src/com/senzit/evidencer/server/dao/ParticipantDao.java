package com.senzit.evidencer.server.dao;

import java.util.List;

import com.senzit.evidencer.server.model.Participant;

public interface ParticipantDao {
	
	int insertParticipant(Participant obj);
	List<Object[]> getParticipants(int caseEventId);

}
