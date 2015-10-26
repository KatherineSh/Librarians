package com.librarians.observer.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;

import com.librarians.model.User;
import com.librarians.observer.EventBuilder;
import com.librarians.observer.EventPublisher;
import com.librarians.observer.event.EmailSendEvent;
import com.librarians.observer.event.NewLibrarianWasAddedEvent;

public class NewLibrarianWelcomeEmailListener {

	@Autowired
	private SimpleMailMessage template;
	@Autowired
	private EventBuilder eventBuilder;
	@Autowired
	private EventPublisher eventPublisher;

	@EventListener(NewLibrarianWasAddedEvent.class)
	public void publishSendEmailEvent(NewLibrarianWasAddedEvent event){
		User librarian = event.getUser();
		if(librarian != null) {
			ApplicationEvent emailSendEvent = fillMessageTemplate(librarian);
			eventPublisher.publish(emailSendEvent);
		}
	}

	private ApplicationEvent fillMessageTemplate(User librarian) {
		StringBuilder text = new StringBuilder();
		text.append("Hi ").append(librarian.getName()).append("\n");

		template.setText(text.toString());
		template.setTo(librarian.getEmail());
		template.setSubject("Welcome");

		ApplicationEvent emailSendEvent = eventBuilder.createEmailSendEvent(template);
		return emailSendEvent;
	}
	
}
