package com.senzit.evidencer.server.controller;

import java.io.FileOutputStream;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.senzit.evidencer.server.subservice.JsonParser;

@RestController
public class TestController {
	
	@RequestMapping(value = "/blobTest", method = RequestMethod.POST)
	public String logout(HttpSession sessionObj,@RequestParam("blobString") String encodedString){
		
		Byte resultCode=0;
		String message="";
		String result="";
		System.out.println("String size : "+encodedString.length());
		System.out.println("Decoding....");
		
		encodedString = encodedString.replaceAll(" ", "+");
		String[] parts = encodedString.split("\\,");
		String beforeFirstDot = parts[1];
		
        byte [] imageInByteArray = Base64.decodeBase64(beforeFirstDot);		
		
		 System.out.println("Decoded. File Size "+imageInByteArray.length+". Writing to file....");

	        try {

	            FileOutputStream out = new FileOutputStream("/home/ramanujan/Desktop/output.mp4");
	            out.write(imageInByteArray);
	            out.close();
	        } catch (Exception e) {
	            e.printStackTrace();

	        }
	        System.out.println("completed!");
		
		JsonParser<String, String> jsonResponse = new JsonParser<String, String>("blobTest",resultCode,message,"blobTestResult",result);
		return jsonResponse.JsonResponseText();		
	}

}
