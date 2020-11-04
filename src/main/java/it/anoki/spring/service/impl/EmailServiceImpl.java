package it.anoki.spring.service.impl;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import it.anoki.spring.service.EmailService;
import it.anoki.spring.service.UserService;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender emailSender;
	@Autowired
	private UserService userService;

	@Override
	public void sendSimpleMessage(String name, String subject, String text) throws Exception{
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("noreply@baeldung.com");
		message.setTo(userService.getFromName(name).getEmail());
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
	}

	public void sendMessageWithAttachment(
			  String name, String subject, String text, String pathToAttachment) throws Exception {
			    MimeMessage message = emailSender.createMimeMessage();
			     
			    MimeMessageHelper helper = new MimeMessageHelper(message, true);
			    
			    helper.setFrom("noreply@baeldung.com");
			    helper.setTo(userService.getFromName(name).getEmail());
			    helper.setSubject(subject);
			    helper.setText(text);
			        
			    FileSystemResource file 
			      = new FileSystemResource(new File(pathToAttachment));
			    helper.addAttachment("Invoice", file);
			 
			    emailSender.send(message);
			}

}
