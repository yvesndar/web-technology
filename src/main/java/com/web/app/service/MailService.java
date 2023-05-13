package com.web.app.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.web.app.model.User;

@Service
public class MailService {

private JavaMailSender mailSender;
	
	public MailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void newAccountEmail(User user) {
		
        SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("yvesndaruhu@gmail.com");
		message.setTo(user.getEmail());
		message.setSubject("New Account");
		message.setText("Dear "+user.getFirstname()+" "+user.getLastname()+", Your new Account has been created. Please log in to continue");
		
		mailSender.send(message); 

		System.out.println("Mail sent successfully");
    }
}
