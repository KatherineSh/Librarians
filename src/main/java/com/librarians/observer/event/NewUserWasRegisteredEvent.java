package com.librarians.observer.event;

import org.springframework.context.ApplicationEvent;

import com.librarians.model.User;

public class NewUserWasRegisteredEvent extends ApplicationEvent {
	
	public User user;
	
	private String appUrl;

	public NewUserWasRegisteredEvent(Object source, User user, String appUrl) {
		super(source);
		this.user = user;
		this.appUrl = appUrl;
	}
	
	public String getAppUrl() {
		return appUrl;
	}

	public User getUser() {
		return user;
	}
	
}
