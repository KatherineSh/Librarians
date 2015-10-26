package com.librarians.observer.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.librarians.observer.event.EmailSendEvent;


@Component
public class EmailSendListener {

	@Autowired
	private MailSender mailSender;

	/**
	 * 	If i need to send another types of template - add here check of instance
	 *  or add dirrerent events for different templates instead of EmailSendEvent
	 */
	@EventListener(EmailSendEvent.class)
	public void processSendingEmail(EmailSendEvent event){
		SimpleMailMessage message = event.getEmailTemplate();
		mailSender.send(message);
	}
	
}
