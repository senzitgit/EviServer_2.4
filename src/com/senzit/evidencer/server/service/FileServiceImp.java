package com.senzit.evidencer.server.service;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import org.springframework.web.multipart.MultipartFile;

import com.senzit.evidencer.server.dao.AttachmentDao;
import com.senzit.evidencer.server.dao.CaseDao;
import com.senzit.evidencer.server.dao.PrivateNoteDao;
import com.senzit.evidencer.server.model.Attachment;
import com.senzit.evidencer.server.model.CaseEventDetail;
import com.senzit.evidencer.server.model.PrivateNote;
import com.senzit.evidencer.server.model.User;
import com.senzit.evidencer.server.subservice.EviService;

public class FileServiceImp implements FileService {
	
	private AttachmentDao attachmentDao;
	private PrivateNoteDao privateDao;
	private CaseDao caseDao;

	public void setCaseDao(CaseDao caseDao) {
		this.caseDao = caseDao;
	}

	public void setAttachmentDao(AttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}

	public void setPrivateDao(PrivateNoteDao privateDao) {
		this.privateDao = privateDao;
	}

	@Override
	public boolean saveAttachment(MultipartFile file, String fileName,String attachmentName,String attachDesc,
			String userName, int caseEventId) {
		
		File savedFile=new File(EviService.getWorkingFolderPath(userName, "Recorder", caseEventId)
				+File.separatorChar+fileName);
		try {
			file.transferTo(savedFile);
		} catch (IllegalStateException | IOException e) {
			
			e.printStackTrace();
			return false;
		}
		
		User user=new User();
		user.setUserName(userName);
		CaseEventDetail eventObj=new CaseEventDetail();
		eventObj.setCaseEventDetailId(caseEventId);
		Attachment attObj=new Attachment(fileName,attachmentName,attachDesc,
				new Timestamp(System.currentTimeMillis()),user,eventObj);
		
		Integer attachId=attachmentDao.insertAttachment(attObj);
		Integer flag=caseDao.setAttachmentFlag(caseEventId);
		if(attachId==null && flag ==0)
			return false;
		
		return true;
	}

	@Override
	public boolean savePrivateNote(String userName, int caseEventId,
			String privateNote) {
		
		User user=new User();
		user.setUserName(userName);
		CaseEventDetail eventObj=new CaseEventDetail();
		eventObj.setCaseEventDetailId(caseEventId);
		
		PrivateNote noteObj=new PrivateNote();
		noteObj.setCaseEventDetail(eventObj);
		noteObj.setCreatedBy(user);
		noteObj.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		noteObj.setPrivateNotes(privateNote);
		
		Integer privateId=privateDao.insertPrivateNote(noteObj);
		Integer flag=caseDao.setPrivateNoteFlag(caseEventId);
		if(privateId==null && flag==0)
			return false;
		return true;
	}

}
