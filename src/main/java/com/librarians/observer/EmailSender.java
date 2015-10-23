package com.librarians.observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;


@Component
public class EmailSender {
	
	@Autowired
	private SimpleMailMessage templateMessage;
	
	@Autowired
	private MailSender mailSender;

	@EventListener(SendEmailEvent.class)
	public void processSendingEmail(SendEmailEvent event){
	
		templateMessage.setTo(event.getEmail());
		templateMessage.setText(event.getText());
		
		System.out.println(templateMessage.toString());
		
		mailSender.send(templateMessage);
		System.out.println("Event listener process event....");
	}
}
