package com.ibm.gbs.ems.utils;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class ApplicationUtils {
	
	private static Logger log = LoggerFactory.getLogger(ApplicationUtils.class);
	
	@Value("com.ibm.ems.fromEmail")
	String fromEmailId;
	@Value("com.ibm.ems.fromEmailPass")
	String fromEmailPass;
	
	public boolean sendMail(String toEmailId,String subject,String mailBody){
    	boolean isSent=false;
    	String emailContent=mailBody;
		try {
			 Properties props = new Properties();
			   props.put("mail.smtp.auth", "true");
			   props.put("mail.smtp.starttls.enable", "true");
			   props.put("mail.smtp.host", "smtp.gmail.com");
			   props.put("mail.smtp.port", "587");
			   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			      protected PasswordAuthentication getPasswordAuthentication() {
			         return new PasswordAuthentication(fromEmailId, fromEmailPass);
			      }
			   });
			   Message msg = new MimeMessage(session);
			   msg.setFrom(new InternetAddress(fromEmailId, false));

			   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmailId));
			   //msg.setSubject(configProps.getPassResetMailSubject());
			   msg.setSubject(subject);
			  // msg.setContent("Tutorials point email", "text/html");
			   msg.setSentDate(new Date());

			   MimeBodyPart messageBodyPart = new MimeBodyPart();
			   messageBodyPart.setContent(emailContent, "text/html");

			   Multipart multipart = new MimeMultipart();
			   multipart.addBodyPart(messageBodyPart);
//			   MimeBodyPart attachPart = new MimeBodyPart();
//			   attachPart.attachFile("/var/tmp/image19.png");
//			   multipart.addBodyPart(attachPart);
			   msg.setContent(multipart);
			   Transport.send(msg);   
			   System.out.println("mail sent successfully");
			   isSent=true;
		} catch (Exception e) {
			e.printStackTrace();
			 log.error("Erro while sending mail",e.getLocalizedMessage());
		}
		return isSent;
    }

}
