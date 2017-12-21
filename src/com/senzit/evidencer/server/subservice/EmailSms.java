package com.senzit.evidencer.server.subservice;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.senzit.evidencer.server.model.EviProperty;

public class EmailSms {
	
	public static void sendMail(String to, String subject, String body) throws IOException, AddressException, MessagingException
	{        
		
		String mailPort 			= EviProperty.evidencerProperty.getProperty("outgoingMailPort");
		String mailServer 			= EviProperty.evidencerProperty.getProperty("outgoingMailServer");
		final String mailUserName 	= EviProperty.evidencerProperty.getProperty("outgoingMailUserName");
		final String mailPassword 	= EviProperty.evidencerProperty.getProperty("outgoingMailPassword");
	
		Properties newProp = new Properties();

		newProp.put("mail.smtp.host", mailServer);  
		newProp.put("mail.smtp.socketFactory.port", mailPort);  
		newProp.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");  
		newProp.put("mail.smtp.auth", "true");  
		newProp.put("mail.smtp.port", "25"); 
		
		Session session = Session.getDefaultInstance(newProp, new javax.mail.Authenticator() 
		{  
			protected PasswordAuthentication getPasswordAuthentication()
			{  
				return new PasswordAuthentication(mailUserName, mailPassword);  
			}  

		});
		
		Message message = new MimeMessage(session); 

		message.setFrom(new InternetAddress(mailUserName));  
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));  
		message.setSubject(subject);  
		message.setText(body);  

		Transport.send(message);
		
	}

}
