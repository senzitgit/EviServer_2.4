package com.senzit.evidencer.server.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	
	boolean saveAttachment(MultipartFile file,String fileName,String attachmentName,String attachDesc,
			String userName,int caseEventId);
	boolean savePrivateNote(String userName,int caseEventId,String privateNote);

}
