package com.librarians.observer.event;

import org.springframework.context.ApplicationEvent;

import com.librarians.model.entity.User;

public class NewLibrarianWasAddedEvent extends ApplicationEvent {

	private User user;

	public NewLibrarianWasAddedEvent(Object source, User user) {
		super(source);
		this.user = user;
	}

	public User getUser() {
		return user;
	}
}
