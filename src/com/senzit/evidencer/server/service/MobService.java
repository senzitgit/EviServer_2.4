package com.senzit.evidencer.server.service;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface MobService {
	
	long checkUserParams(String email);
	String register(String email,String password);
	String insertNewFile(MultipartFile[] file,
			String detail,String desc,String latitude,String longitude,
			String userName,boolean createdByFlag) throws IllegalStateException, IOException;
	Hashtable<String,Object> getMobFileLink(String randomCode);
	List<Hashtable<String,Object>> getMobFileLink();
	long login(String emailId,String password);
	List<Hashtable<String, String>> getMobFileStatus(String userName);
	void upadtePassword(String email,String newPassword);
	public int setMobFileStatus(String fileId, String status);
}
