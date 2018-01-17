package com.senzit.evidencer.server.subservice;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.senzit.evidencer.server.model.EviProperty;

public class RebbonService {
	
	static Logger log = Logger.getLogger(RebbonService.class.getName());
	
	public String requestToRebbon(String postData){
		
		String rebbonUrl="http://192.168.10.50:8080/Rebbon/RebbonHandler";
		////////////////////////////////////////////////////
		log.debug("RebbonUrl:"+rebbonUrl);
		log.debug("RebbonPostData:"+postData);
		//////////////////////////////////////////////////
		
		URL url;
		HttpURLConnection connection;
		try {
			url = new URL(rebbonUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setConnectTimeout(5000);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Length",  String.valueOf(postData.length()));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		StringBuilder responseSB = new StringBuilder();
		// Write data
		OutputStream os;
		try {
			os = connection.getOutputStream();
			os.write(postData.getBytes());
			
			// Read response
			
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			         
			String line;
			while ( (line = br.readLine()) != null)
				responseSB.append(line);
			
			// Close streams
			br.close();
			os.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally{
			
		}
		return responseSB.toString();
	}
	
	public Properties startRebbonRecord(){		//Reboon response resultCode must be checked. Not implemented in Rebbon
		
		String responseJson=requestToRebbon("origin=StartRebbon");
		////////////////////
		log.debug("StartRebbon");
		log.debug("RebbonResponse:"+responseJson);
		////////////////////

		Properties result=new Properties();
		if(responseJson==null)
			return null;
		JSONObject jsonObj;
		try {
			jsonObj = new JSONObject(responseJson);

			
			jsonObj=jsonObj.getJSONObject("response");
			jsonObj=jsonObj.getJSONObject("liveCamDetails");		
			
			Properties feed1 = new Properties();
			Properties feed2 = new Properties();
			Properties feed3 = new Properties();
			Properties feed4 = new Properties();

			
			JSONObject cam1 = new JSONObject();
			JSONObject cam2 = new JSONObject();
			JSONObject cam3 = new JSONObject();
			JSONObject cam4 = new JSONObject();
			
						
			cam1=jsonObj.getJSONObject("CAM1");
			feed1.put("Url",(String) cam1.get("Url"));
			feed1.put("Aud",(String) cam1.get("Aud"));			
			result.put("CAM1", feed1);
		
			cam2=jsonObj.getJSONObject("CAM2");
			feed2.put("Url",(String) cam2.get("Url"));
			feed2.put("Aud",(String) cam2.get("Aud"));			
			result.put("CAM2", feed2);
			
			
			cam3=jsonObj.getJSONObject("CAM3");
			feed3.put("Url",(String) cam3.get("Url"));
			feed3.put("Aud",(String) cam3.get("Aud"));				
			result.put("CAM3", feed3);
			
			
			cam4=jsonObj.getJSONObject("CAM4");
			feed4.put("Url",(String) cam4.get("Url"));
			feed4.put("Aud",(String) cam4.get("Aud"));		
			result.put("CAM4", feed4);
			
			
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		
		return result;
	}
	
	public boolean startRebbon(){
		
		String responseJson=requestToRebbon("origin=StartMediaServer");
		////////////////////
		log.debug("StartMediaServer");
		log.debug("RebbonResponse:"+responseJson);
		////////////////////
		if(responseJson==null)
			return false;
		
		////Reboon response resultCode must be checked. Not implemented in Rebbon
		
		return true;
	}
	
	public boolean stopRebbon(String userName,int caseEventId,String caseNo,String workingFolderPath) throws JSONException{
		
		
		JSONArray jArray = new JSONArray();
	    JSONObject obj;
		
		
		/*File workingFolder = new File(workingFolderPath);		
	    File[] listOfFiles = workingFolder.listFiles();
	    String attachmentPath=EviService.getWorkingFolderWebPath(userName, "Recorder", caseEventId);
	    
	    
	    System.err.println("Length"+listOfFiles.length);
	    for (int i = 0; i < listOfFiles.length; i++) {
	      if (listOfFiles[i].isFile()) {
	    	  
	    	  obj  = new JSONObject();     
	    	  obj.put("image"+i, attachmentPath+File.separatorChar+listOfFiles[i].getName());
	    	  obj.put("file"+i,listOfFiles[i].getName());
	    	  jArray.put(obj);     
	      }
	    }*/
		
	    String jsonWatermark = "{\"caseNo\":\""+caseNo+"\"}";
		
		String responseJson=requestToRebbon("origin=StopEviRebbon&caseEventId="+caseEventId+"&jsonImage="+jArray.toString()
				+"&jsonWatermark="+jsonWatermark);
		////////////////////
		log.debug("StopEviRebbon");
		log.debug("RebbonResponse:"+responseJson);
		////////////////////
		if(responseJson==null)
			return false;
		////Reboon response resultCode must be checked. Not implemented in Rebbon
		return true;
	}	
	
	public Hashtable<String,Object> getAVLinks(int caseEventId,String userName){		//Reboon response resultCode must be checked. Not implemented in Rebbon
		
		String responseJson=requestToRebbon("origin=GetEviAvFiles&caseEventId="+caseEventId+"&userName="+userName);
		////////////////////
		log.debug("GetEviAvFiles");
		log.debug("RebbonResponse:"+responseJson);
		////////////////////

		Hashtable<String,Object> result=new Hashtable<String,Object>();
		if(responseJson==null)
			return null;
		JSONObject jsonObj;
		try {
			jsonObj = new JSONObject(responseJson);			
			jsonObj=jsonObj.getJSONObject("response");
			jsonObj=jsonObj.getJSONObject("RecordedFeeds");
			
			
			///////// CHECK IF FILES EXIST /////
			
			String v1 = (String)jsonObj.get("VideoFeed1");
			String v2 = (String)jsonObj.get("VideoFeed2");
			String v3 = (String)jsonObj.get("VideoFeed3");
			String v4 = (String)jsonObj.get("VideoFeed4");
			
			
			if( ! (RebbonService.exists(v1))){
				// ONLY FIRST VIDEO IS CHECKED
				
				return result;
			}
			
			///////////////////////////////////

			result.put("videoFeed1", v1);
			result.put("videoFeed2", v2);
			result.put("videoFeed3", v3);
			result.put("videoFeed4", v4);
			
			result.put("audioFeed1", (String) jsonObj.get("AudioFeed1"));
			result.put("audioFeed2", (String) jsonObj.get("AudioFeed2"));
			result.put("audioFeed3", (String) jsonObj.get("AudioFeed3"));
			result.put("audioFeed4", (String) jsonObj.get("AudioFeed4"));
			
			
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		
		return result;
	}
	
	public JSONObject getAttachmentLinks(int caseEventId,String userName){		//Reboon response resultCode must be checked. Not implemented in Rebbon
		
		String responseJson=requestToRebbon("origin=GetEviAttachment&caseEventId="+caseEventId+
				"&userName="+userName);
		////////////////////
		log.debug("GetEviAttachment");
		log.debug("RebbonResponse:"+responseJson);
		////////////////////
		if(responseJson==null)
			return null;
		JSONObject jsonObj;
		try {
			jsonObj = new JSONObject(responseJson);			
			jsonObj=jsonObj.getJSONObject("response");
			
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}		
		return jsonObj;
	}
	
	public Properties getMediaLinks(){
		
		String responseJson=requestToRebbon("origin=GetLiveFeeds");
		////////////////////
		log.debug("GetLiveFeeds");
		log.debug("RebbonResponse:"+responseJson);
		////////////////////

		Properties result=new Properties();
		if(responseJson==null)
			return null;
		JSONObject jsonObj;
		try {
			jsonObj = new JSONObject(responseJson);

			
			jsonObj=jsonObj.getJSONObject("response");
			jsonObj=jsonObj.getJSONObject("liveCamDetails");		
			
			Properties feed1 = new Properties();
			Properties feed2 = new Properties();
			Properties feed3 = new Properties();
			Properties feed4 = new Properties();
			
			JSONObject cam1 = new JSONObject();
			JSONObject cam2 = new JSONObject();
			JSONObject cam3 = new JSONObject();
			JSONObject cam4 = new JSONObject();
						
			cam1=jsonObj.getJSONObject("CAM1");
			feed1.put("Url",(String) cam1.get("Url"));
			feed1.put("Aud",(String) cam1.get("Aud"));			
			result.put("CAM1", feed1);
		
			cam2=jsonObj.getJSONObject("CAM2");
			feed2.put("Url",(String) cam2.get("Url"));
			feed2.put("Aud",(String) cam2.get("Aud"));			
			result.put("CAM2", feed2);
			
			
			cam3=jsonObj.getJSONObject("CAM3");
			feed3.put("Url",(String) cam3.get("Url"));
			feed3.put("Aud",(String) cam3.get("Aud"));				
			result.put("CAM3", feed3);
			
			
			cam4=jsonObj.getJSONObject("CAM4");
			feed4.put("Url",(String) cam4.get("Url"));
			feed4.put("Aud",(String) cam4.get("Aud"));		
			result.put("CAM4", feed4);
			
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		
		return result;
	}
	
//	public boolean uploadFile(int caseEventId){
//		
//		String responseJson=requestToRebbon("origin=uploadFile&caseEventId="+caseEventId);
//		////////////////////
//		log.debug("uploadFile");
//		log.debug("RebbonResponse:"+responseJson);
//		////////////////////
//
//		
//		return true;
//	}
	
//	public boolean clear(int caseEventId){
//		
//		String responseJson=requestToRebbon("origin=ClearWorkingDirectory&caseEventId="+caseEventId);
//		////////////////////
//		log.debug("ClearWorkingDirectory");
//		log.debug("RebbonResponse:"+responseJson);
//		////////////////////
//
//		
//		return true;
//	}
	
	public static boolean exists(String URLName){
		
		try {
			
			HttpURLConnection.setFollowRedirects(false);
			// note : you may also need
			//        HttpURLConnection.setInstanceFollowRedirects(false)
			HttpURLConnection con = (HttpURLConnection) new URL(URLName).openConnection();
			con.setRequestMethod("HEAD");
			return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		}
	    catch (Exception e) {
	       e.printStackTrace();
	       return false;
	    }
	}

}
