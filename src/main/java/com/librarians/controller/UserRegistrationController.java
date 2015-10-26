package com.librarians.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.librarians.model.User;
import com.librarians.observer.EventBuilder;
import com.librarians.observer.EventPublisher;
import com.librarians.service.UserService;

@Controller
public class UserRegistrationController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EventPublisher eventPublisher;
	
	@Autowired
	private EventBuilder eventBuilder;
	
	public static final String REGISTRATION_CONFIRM_PAGE_SUFFIX = "confirmRegistration";
	
	static Logger log = Logger.getLogger(UserRegistrationController.class.getName());

	@RequestMapping(path="/register", method=RequestMethod.POST)
	public String registerUser(

			@Valid User user, 
			BindingResult result, 
			Model model, 
			RedirectAttributes redirectAttrs,
			HttpServletRequest request){
		
		
		if(result.hasErrors()) {
			System.out.println("Validation errors in registration user form");
			return "login";
		} 
		result = userService.isUserUnique(user, result);
		if (result.hasErrors()){
			return "login";
		}
		
		Integer userId = userService.createUser(user);
		if(userId != null) {	
			String url = request.getRequestURL().toString();
			ApplicationEvent event = eventBuilder.createNewUserWasRegisteredEvent(user, url);
			eventPublisher.publish(event);
			
			redirectAttrs.addFlashAttribute("newUserCreated", user.getName());
		} else {
			//publish another event
		}
		return "redirect:/login";
	}
	
	@RequestMapping(path="/register/confirmRegistration/{tokenId}", method=RequestMethod.GET)
	public String confirmUserRegistration(
			@PathVariable String tokenId,
			Model model,
			RedirectAttributes redirectAttr){
		
		System.out.println("confirmRegistration................");
		
		boolean result = userService.setUserEnabled(tokenId);
		redirectAttr.addFlashAttribute("registrationConfirmationError", result);
		return "redirect:/login";
	}
}
