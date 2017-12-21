package com.senzit.evidencer.server.dao;

import java.util.List;

import com.senzit.evidencer.server.model.MobData;
import com.senzit.evidencer.server.model.MobFile;
import com.senzit.evidencer.server.model.MobUser;

public interface MobDao {
	
	String insertUser(MobUser obj);
	long checkUserEmailId(String email);
	MobUser getUser(String userName);
	
	long checkRandomCodeExists(String randomCode);
	String insertMobData(MobData file);
	Integer insertMobFile(MobFile file);
	
	MobData getMobData(String randomCode);
	List<MobFile> getMobFiles(MobData mobData);
	List<MobData> getAllMobData();
	
	long authenticate(String email,String password);
	
	List<Object[]> getUserMobDetail(String userName);
	int setMobFileStatus(String fileId,String status);
	
	void updatePassword(String email,String newPassword);

}
