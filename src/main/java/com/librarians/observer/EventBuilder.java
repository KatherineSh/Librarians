package com.librarians.observer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.librarians.model.User;
import com.librarians.observer.event.EmailSendEvent;
import com.librarians.observer.event.NewLibrarianWasAddedEvent;
import com.librarians.observer.event.NewUserWasRegisteredEvent;

@Component("eventBuilder")
public class EventBuilder {
	
	@Autowired
	private EventPublisher eventPublisher;
	
	public NewUserWasRegisteredEvent createNewUserWasRegisteredEvent(User user, String url){
		NewUserWasRegisteredEvent event = new NewUserWasRegisteredEvent(eventPublisher, user, url);
		return event;
	}
	
	public NewLibrarianWasAddedEvent createNewListenerWasAddedEvent(User librarian){
		NewLibrarianWasAddedEvent event = new NewLibrarianWasAddedEvent(eventPublisher, librarian);
		return event;
	}
	
	public EmailSendEvent createEmailSendEvent(SimpleMailMessage template){
		EmailSendEvent event = new EmailSendEvent(eventPublisher, template);
		return event;
	}
}
