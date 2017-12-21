package com.senzit.evidencer.server.service;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import com.senzit.evidencer.server.dao.MobDao;
import com.senzit.evidencer.server.model.EviProperty;
import com.senzit.evidencer.server.model.MobData;
import com.senzit.evidencer.server.model.MobFile;
import com.senzit.evidencer.server.model.MobUser;

public class MobServiceImp implements MobService{
	
	private MobDao mobDao;

	public void setMobDao(MobDao mobDao) {
		this.mobDao = mobDao;
	}

	@Override
	public long checkUserParams(String email) {
		
		return mobDao.checkUserEmailId(email);
	}

	@Override
	public String register(String email, String password) {
		
		MobUser obj = new MobUser();
		obj.setEmail(email);
		obj.setPassword(password);
		return mobDao.insertUser(obj);
	}

	@Override
	public String insertNewFile(MultipartFile[] fileArray,String detail,String desc,
			String latitude,String longitude,
			String userName,boolean createdByFlag) throws IllegalStateException, IOException {
		
		
		// FIRST CHECK THIS RANDOM CODE EXISTS WITH THIS USER
		MobUser user = new MobUser(userName);
		String randomCode = null;
		do{
			randomCode = ((Long)(Math.round(Math.random()* 89999) + 10000)).toString();
		}while(mobDao.checkRandomCodeExists(randomCode)>0);
		
		char separator=File.separatorChar;
		String workingFolder =  EviProperty.webAppFolder+separator
	    		+EviProperty.evidencerProperty.getProperty("defaultWorkingFolder");
		
		File folder1 = new File(workingFolder+separator+"EviMob"+separator+randomCode);
		if(!folder1.exists())
			folder1.mkdir();
		String fileName=null;
		for(int i=0;i<fileArray.length;i++){
			
			MultipartFile file = fileArray[i];
			fileName = i+file.getOriginalFilename();
			File savedFile=new File(workingFolder+separator+"EviMob"+separator+randomCode+separator+fileName);
			file.transferTo(savedFile);
			System.out.println("\nFile "+fileName+" with size "+file.getSize()+" saved successfully\n");
		}
		
		//Database updation is only after successfully saving all files
		
		MobData newData = new MobData();
		String ext = FilenameUtils.getExtension(workingFolder+separator+"EviMob"+separator+randomCode+separator+fileName);
		if("mp3".equalsIgnoreCase(ext) || "wav".equalsIgnoreCase(ext) || "amr".equalsIgnoreCase(ext))
			newData.setFileType("audio");
		else if("mp4".equalsIgnoreCase(ext) || "mov".equalsIgnoreCase(ext))
			newData.setFileType("video");
		else if("jpg".equalsIgnoreCase(ext) || "jpeg".equalsIgnoreCase(ext) || "png".equalsIgnoreCase(ext))
			newData.setFileType("image");
		else
			newData.setFileType("unknown");
		
		newData.setDetail(detail);
		newData.setDescription(desc);
		newData.setLatitude(latitude);
		newData.setLongitude(longitude);
		newData.setRandomCode(randomCode);
		newData.setStatus("New");
		if(createdByFlag) newData.setCreatedBy(user);
		newData.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		mobDao.insertMobData(newData);
		
		for(int i=0;i<fileArray.length;i++){
			
			MobFile newFile = new MobFile();
			newFile.setFileName(i+fileArray[i].getOriginalFilename());
			newFile.setMobData(newData);
			mobDao.insertMobFile(newFile);
		}
		return randomCode;
	}

	@Override      //Returns NULL if no such file****//Returns EMPTY TABLE if file not in folder
	public Hashtable<String, Object> getMobFileLink(String randomCode) {
		
		Hashtable<String, Object> result = new Hashtable<String, Object>();
		MobData fileObj = mobDao.getMobData(randomCode);
		if(fileObj==null)
			return null;   //Returns NULL if no such file
		
		char separator=File.separatorChar;
		String folder = EviProperty.webAppFolder+separator
	    		+EviProperty.evidencerProperty.getProperty("defaultWorkingFolder")
	    		+separator+"EviMob"+separator+randomCode;
		String folderWebPath = EviProperty.ipAddress+"/"
			+EviProperty.evidencerProperty.getProperty("defaultWorkingFolder")+"/"
			+"EviMob/"+randomCode;
		
		List<MobFile> fileList = mobDao.getMobFiles(fileObj);
		List<String> fileNames = new ArrayList<String>();
		for(MobFile file : fileList){
			
			String fileName = file.getFileName();
			if( !(new File(folder+separator+fileName)).exists() )
				return result;    //Returns EMPTY TABLE if file not in folder
			fileNames.add(folderWebPath+"/"+fileName);
		}		
		result.put("fileLink", fileNames);
		result.put("randomCode", randomCode);
		result.put("fileType", fileObj.getFileType());
		result.put("fileDetail", fileObj.getDetail());
		result.put("fileDesc", fileObj.getDescription());
		result.put("latitude", fileObj.getLatitude());
		result.put("longitude", fileObj.getLongitude());
		result.put("createdOn", fileObj.getCreatedOn().toString());
		result.put("status", fileObj.getStatus());
		MobUser user = fileObj.getCreatedBy();
		result.put("createdBy", (user==null)?"":user.getEmail());
		
		return result;
	}
	
	@Override
	public List<Hashtable<String, Object>> getMobFileLink() {
		
		List<MobData> dataListObj = mobDao.getAllMobData();
		if(dataListObj.isEmpty())
			return null;
		List<Hashtable<String, Object>> returnList = new ArrayList<Hashtable<String, Object>>();
		for(MobData dataObj : dataListObj){
			
			Hashtable<String, Object> prop = getMobFileLink(dataObj.getRandomCode());
			if(!prop.isEmpty() )returnList.add(prop);
		}
		return returnList;
	}

	@Override
	public long login(String emailId, String password) {
		
		return mobDao.authenticate(emailId, password);
	}

	@Override
	public List<Hashtable<String, String>> getMobFileStatus(String userName) {
		
		ArrayList<Hashtable<String, String>> result = new ArrayList<Hashtable<String, String>>();
		List<Object[]> fileList = mobDao.getUserMobDetail(userName);
		for(Object[] objArray : fileList){
			
			Hashtable<String, String> table = new Hashtable<String, String>();
			table.put("randomCode", objArray[0].toString());
			table.put("status", objArray[1].toString());
			table.put("createdOn", objArray[2].toString());
			result.add(table);
		}
		return result;
	}

	@Override
	public void upadtePassword(String email, String newPassword) {
		
		mobDao.updatePassword(email, newPassword);
		
	}

	@Override
	public int setMobFileStatus(String randomCode, String status) {
		return mobDao.setMobFileStatus(randomCode, status);
	}

}
