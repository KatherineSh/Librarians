package com.librarians.observer;

import org.springframework.context.ApplicationEvent;

public class SendEmailEvent extends ApplicationEvent {

	private String email;
	private String text;
	
	public SendEmailEvent(Object source, String email, String text) {
		super(source);
		this.email = email;
		this.text = text;
	}

	public String getEmail() {
		return email;
	}

	public String getText() {
		return text;
	}
}
