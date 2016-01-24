package com.librarians.observer.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.librarians.controller.UserRegistrationController;
import com.librarians.model.entity.User;
import com.librarians.observer.EventBuilder;
import com.librarians.observer.EventPublisher;
import com.librarians.observer.event.EmailSendEvent;
import com.librarians.observer.event.NewUserWasRegisteredEvent;
import com.librarians.service.userToken.TokenGenerator;

@Component
public class NewUserRegistrationConfirmationEmailListener {

	@Autowired
	private SimpleMailMessage template;
	@Autowired
	private EventBuilder eventBuilder;
	@Autowired
	private EventPublisher eventPublisher;
	@Autowired
	private TokenGenerator tokenGenerator;

	@EventListener(NewUserWasRegisteredEvent.class)
	public void publishSendEmailEvent(NewUserWasRegisteredEvent event){
		
		User user = event.getUser();
		String appUrl = event.getAppUrl();
		
		if(user != null && appUrl != null) {
			EmailSendEvent emailSendEvent = fillMessageTemplate(user, appUrl);
			eventPublisher.publish(emailSendEvent);
		}
	}

	private EmailSendEvent fillMessageTemplate(User user, String appUrl) {
		String token = tokenGenerator.generateAndSaveToken(user);

		StringBuilder text = new StringBuilder();
		text.append("Hi ").append(user.getName()).append("\n\n")
				.append("You've created a new account on librarians site. Confirm your email by clicking ")
				.append(appUrl).append("/").append(UserRegistrationController.REGISTRATION_CONFIRM_PAGE_SUFFIX).append("/").append(token)
				.append(" link to complete registration.").append("\n\n")
				.append("Synserly,").append("\n").append("yours Librarians");

		template.setText(text.toString());
		template.setTo(user.getEmail());
		template.setSubject("Registration confirmation");

		EmailSendEvent emailSendEvent = eventBuilder.createEmailSendEvent(template);
		return emailSendEvent;
	}

}
