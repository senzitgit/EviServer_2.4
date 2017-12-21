package com.senzit.evidencer.server.model;

public class MobFile {
	
	private int fileId;
	private String fileName;
	private MobData mobData;
	
	public MobFile(){}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public MobData getMobData() {
		return mobData;
	}

	public void setMobData(MobData mobData) {
		this.mobData = mobData;
	}

}
