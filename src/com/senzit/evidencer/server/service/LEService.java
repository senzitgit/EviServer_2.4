package com.senzit.evidencer.server.service;

public interface LEService {
	
	public String insertLocation(String userName,String lePath);
	public String getLocation(String userName);
	public int updateLocation(String userName,String loc);
}
