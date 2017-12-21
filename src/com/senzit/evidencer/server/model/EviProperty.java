package com.senzit.evidencer.server.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class EviProperty implements ServletContextListener{
	
	public static Properties evidencerProperty;
	public static String webAppFolder;
	public static String ipAddress;
	public static boolean recordStatus;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		///////////////////////////////////////////////////////////////////////////////
		String propertyFilePath=(String)arg0.getServletContext().getInitParameter("propertyFilePath");
		evidencerProperty=new Properties();
		
		
		
		
		try {
			InputStream input = new FileInputStream(propertyFilePath);
			evidencerProperty.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		///////////////////////////////////////////////////////////////////////////////
		char separator=File.separatorChar;
		String realPath=arg0.getServletContext().getRealPath("");        
        realPath=realPath.substring(0, realPath.lastIndexOf(separator));        
        realPath=realPath.substring(0, realPath.lastIndexOf(separator));        
        realPath=realPath+separator+"webapps";        
        webAppFolder=realPath;  
        ///////////////////////////////////////////////////////////////////////////////
        ipAddress="http://"+evidencerProperty.getProperty("myIp")+":"+evidencerProperty.getProperty("portNumber");
        
        recordStatus=false;
	}

}
