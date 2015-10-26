package com.librarians.observer;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component("eventPublisher")
public class EventPublisher implements ApplicationEventPublisherAware{

	private ApplicationEventPublisher publisher;
	
	/**
	 * Spring does set automatically when detect ApplicationEventPublisherAware
	 *  in reality, this parameter is Spring's container itself
	 *  i'm just speak with it through ApplicationEventPublisher interface.
	 */
	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}

	 public void publish(ApplicationEvent event) {
		 this.publisher.publishEvent(event);
		 //System.out.println("EmailEvent was published");
	 }

}
