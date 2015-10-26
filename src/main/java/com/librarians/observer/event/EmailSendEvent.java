package com.librarians.observer.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.mail.SimpleMailMessage;

public class EmailSendEvent extends ApplicationEvent {

	@Autowired
	private SimpleMailMessage emailTemplate;
	
	public EmailSendEvent(Object source, SimpleMailMessage template) {
		super(source);
		this.emailTemplate = template;
	}

	public SimpleMailMessage getEmailTemplate() {
		return emailTemplate;
	}
}
