package com.senzit.evidencer.server.subservice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.HibernateException;
import org.hibernate.exception.JDBCConnectionException;

import com.senzit.evidencer.server.model.EviProperty;

public class EviService {
	
	public static String jsonForWorklight(String json){
		
		String tempJson = json.substring(1, (json.length()-1));
		return tempJson.replaceAll("\\\\", "");
	}
	
	public static String createWorkingFolder(int caseEventDetailId,String componentName,String userName){
		
		char separator=File.separatorChar;
		
		String workingFolderPath=EviProperty.webAppFolder+separator
				+"APPDATA";
		File workingFolder 	= new File(workingFolderPath);
		if(!workingFolder.exists())
			workingFolder.mkdir();
		
		String locationFolderPath=workingFolderPath + separator
				+ "1001";
		File locationFolder 	  = new File(locationFolderPath); 
		if(!locationFolder.exists())
			locationFolder.mkdir();
		
		String courtFolderPath=locationFolderPath + separator
				+ "1001";
		File courtFolder = new File(courtFolderPath);
		if(!courtFolder.exists())
			courtFolder.mkdir();
		
		String componentFolderPath=courtFolderPath + separator + componentName;
		File componentFolder = new File(componentFolderPath);
		if(!componentFolder.exists())
			componentFolder.mkdir();
		
		String userFolderPath=componentFolderPath + separator + userName;
		File userFolder = new File(userFolderPath);
		if(!userFolder.exists())
			userFolder.mkdir();
		
		String caseFolderPath=userFolderPath + separator + caseEventDetailId;
		File caseFolder = new File(caseFolderPath);
		if(!caseFolder.exists())
			caseFolder.mkdir();
		
		return caseFolderPath;

	}
	
	public static String getWorkingFolderWebPath(String userName,String componentName,int caseEventId){
		
		return EviProperty.ipAddress+"/"
	    		+"APPDATA"+"/"
	    		+"1001"+"/"
	    		+"1001"+"/"
	    		+componentName+"/"+userName+"/"+caseEventId;
	}
	
	public static String getWorkingFolderPath(String userName,String componentName,int caseEventId){
		
		char separator=File.separatorChar;
		return EviProperty.webAppFolder+separator
	    		+"APPDATA"+separator
	    		+"1001"+separator
	    		+"1001"+separator
	    		+componentName+separator+userName+separator+caseEventId;
	}
	
	public static String formatTime(Timestamp timestamp){
		
		SimpleDateFormat sdf = new SimpleDateFormat("E,dd-MM-YYYY,HH:mm:ss");
		return sdf.format(timestamp.getTime());
	}
	
	public static String getProPic(String userName){
		
		String path=EviProperty.webAppFolder+File.separatorChar+"EviProPics";
		String ip=EviProperty.ipAddress+"/EviProPics/"+userName;
		String proPic="";
		File folder=new File(path);
		if(folder.exists()){
			path+=File.separatorChar+userName;
			folder= new File(path);
			if(folder.exists()){
				String[] proPicArray=folder.list();
				if(proPicArray.length>0)
					proPic=ip+"/"+proPicArray[0];
			}
		}
		
		return proPic;
	}
	
//	public static String insertProPic(String userName,MultipartFile file){
//		
//		String path=EviProperty.webAppFolder+File.separatorChar+"EviProPics";
//		boolean flag=true;
//		File folder=new File(path);
//		if(!folder.exists())
//			flag=folder.mkdir();
//		if(flag){
//			
//			path+=File.separatorChar+userName;
//			folder=new File(path);
//			if(!folder.exists())
//				flag=folder.mkdir();
//			if(flag){
//				
//				String[] proPicArray=folder.list();
//				String proPic;
//				for(int i=0;i<proPicArray.length;i++){					
//					
//					proPic=path+File.separatorChar+proPicArray[i];
//					File prevImage= new File(proPic);
//					flag=prevImage.delete();
//				}
//				if(flag){
//					
//					try {
//						file.transferTo(new File(path+File.separatorChar+file.getOriginalFilename()));
//					} catch (IllegalStateException | IOException e) {
//						
//						e.printStackTrace();
//						return null;
//					}			
//					
//				}
//				
//				
//			}
//		}
//		return flag ? EviProperty.ipAddress+"/EviProPics/"+userName+"/"+file.getOriginalFilename() : null;
//		
//	}
	
	public static String insertProPic2(String userName,String imageString){
				        
        String path=EviProperty.webAppFolder+File.separatorChar+"EviProPics";
		boolean flag=true;
		File folder=new File(path);
		if(!folder.exists())
			flag=folder.mkdir();
		if(flag){
			
			path+=File.separatorChar+userName;
			folder=new File(path);
			if(!folder.exists())
				flag=folder.mkdir();
			if(flag){
				
				String[] proPicArray=folder.list();
				String proPic;
				for(int i=0;i<proPicArray.length;i++){					
					
					proPic=path+File.separatorChar+proPicArray[i];
					File prevImage= new File(proPic);
					flag=prevImage.delete();
				}
				if(flag){
					
					imageString = imageString.replaceAll(" ", "+");
					String[] parts = imageString.split("\\,");
					String beforeFirstDot = parts[1];
					
					OutputStream outputStream = null;
			        byte [] imageInByteArray = Base64.decodeBase64(beforeFirstDot);
			        try {
			            outputStream = new FileOutputStream(path+File.separatorChar+userName+".jpg");
			            outputStream.write(imageInByteArray);
			            return EviProperty.ipAddress+"/EviProPics/"+userName+"/"+userName+".jpg";
			        } catch (IOException e) {
			            e.printStackTrace();
			        } finally{
			            try {
			                outputStream.close();
			            } catch (IOException e) {
			                e.printStackTrace();
			            }
			        }
					
				}
			}
		}
		
		return null;
        
	}
	
	public static boolean deleteProPic(String userName){
		
		String path=EviProperty.webAppFolder+File.separatorChar+"EviProPics"+File.separatorChar+userName;
		boolean flag=false;
		File folder=new File(path);
		if(folder.exists()){
			
			String[] proPicArray=folder.list();
			String proPic;
			for(int i=0;i<proPicArray.length;i++){					
				
				proPic=path+File.separatorChar+proPicArray[i];
				File prevImage= new File(proPic);
				flag=prevImage.delete();
			}
		}
		return flag;
	}
	
	public static String getDefaultLocation() {
		
		return EviProperty.evidencerProperty.getProperty("defaultLocationName");
	}
	
	public static String getServerIp() {
		
		return EviProperty.evidencerProperty.getProperty("myIp");
	}
	
	public static String getExceptionMessage(Exception e){
		
		if(e instanceof HibernateException ) return "EvidencerError1001 : Database Error";
		if(e instanceof JDBCConnectionException ) return "EvidencerError1002 : Database unreachable";
		return "Unknown Exception";
	}

}
