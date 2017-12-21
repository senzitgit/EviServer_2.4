package com.senzit.evidencer.server.service;

import com.senzit.evidencer.server.dao.LEDao;
import com.senzit.evidencer.server.model.LandEPath;
import com.senzit.evidencer.server.model.User;

public class LEServiceImp implements LEService {
	
	private LEDao leDao;

	public void setLeDao(LEDao leDao) {
		this.leDao = leDao;
	}

	@Override
	public String insertLocation(String userName, String lePath) {
		
		LandEPath obj = new LandEPath();
		User user = new User();
		user.setUserName(userName);
		obj.setUser(user);
		obj.setLePath(lePath);
		return leDao.insertLocation(obj);
	}

	@Override
	public String getLocation(String userName) {
		
		return leDao.getLocation(userName);
	}

	@Override
	public int updateLocation(String userName, String loc) {
		
		return leDao.updateLocation(userName, loc);
	}

}
