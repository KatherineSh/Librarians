package com.librarians.observer;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component("emailService")
public class EmailService implements ApplicationEventPublisherAware{

	private ApplicationEventPublisher publisher;
	
	/**
	 * Spring does set automatically when detect ApplicationEventPublisherAware
	 *  reality, this parameter is Spring container itself
	 *  i'm just speak with it through ApplicationEventPublisher interface.
	 */
	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}

	 public void sendEmail(String address, String text) {
		 SendEmailEvent event = new SendEmailEvent(this, address, text);
		 this.publisher.publishEvent(event);
		 System.out.println("EmailEvent was published");
	 }
}
