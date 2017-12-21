package com.senzit.evidencer.server.dao;

import java.util.List;

import com.senzit.evidencer.server.model.Attachment;

public interface AttachmentDao {
	
	Integer insertAttachment(Attachment obj);
	List<Object[]> getAttachmentInfo(int caseEventId);

}
