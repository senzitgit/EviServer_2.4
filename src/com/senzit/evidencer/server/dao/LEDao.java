package com.senzit.evidencer.server.dao;

import com.senzit.evidencer.server.model.LandEPath;

public interface LEDao {
	
	public String insertLocation(LandEPath obj);
	public String getLocation(String userName);
	public int updateLocation(String userName,String loc);

}
