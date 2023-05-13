package com.web.app.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailSenderConfig {
	

	    @Bean("javaMailSender")
	    JavaMailSender javaMailSender() {
	        JavaMailSenderImpl sender = new JavaMailSenderImpl();
	        sender.setHost("smtp.gmail.com");
	        sender.setPort(587);
	        sender.setUsername("yvesndaruhu@gmail.com");
	        sender.setPassword("prqdxhfxzlotyyem");

	        Properties props = sender.getJavaMailProperties();
	        props.put("mail.transport.protocol", "smtp");
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.debug", "true");

	        return sender;
	    
	}
}