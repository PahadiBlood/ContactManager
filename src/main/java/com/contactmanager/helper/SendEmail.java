package com.contactmanager.helper;

import java.util.Properties;
import java.util.Random;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.contactmanager.enitiy.OTP;
import com.contactmanager.service.UserService;
import com.sun.mail.util.MailSSLSocketFactory;

@Service
public class SendEmail {

	Random random = new Random(999999);
	@Autowired
	private JavaMailSenderImpl mailSender;

	@Autowired
	private UserService service;
	
	public boolean sendEmail(String email) {
		try {

	      
			int otp=random.nextInt(999999);
			
			service.save(new OTP(otp, email));
			
			Properties properties = new Properties();
			MailSSLSocketFactory sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
			properties.put("mail.smtp.ssl.socketFactory", sf);
			properties.put("mail.smtp.starttls.enable", "true");
			
			MimeMessage message=mailSender.createMimeMessage();
			MimeMessageHelper helper=new MimeMessageHelper(message);
			
			String text="<h2>Your one time password is :"+otp+"</h2>";
			helper.setFrom("contact@manager.com","Contact Manager");
			helper.setTo(email);
			helper.setSubject("Password Change Request");
			helper.setText(text,true);

			mailSender.setJavaMailProperties(properties);
			mailSender.send(message);
			return true;
		} catch (Exception e) {
			System.out.println(e);
			e.getStackTrace();
			return false;
		}

		    }
}
