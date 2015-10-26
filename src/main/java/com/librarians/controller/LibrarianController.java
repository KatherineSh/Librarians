package com.librarians.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.librarians.model.User;
import com.librarians.model.UserRole;
import com.librarians.observer.EventBuilder;
import com.librarians.observer.EventPublisher;
import com.librarians.service.UserService;

@Controller
public class LibrarianController {

	static Logger log = Logger.getLogger(LibrarianController.class.getName());
	
	@Autowired
	private UserService userService;
	@Autowired
	private EventBuilder eventBuilder;
	@Autowired
	private EventPublisher eventPublisher;
	
	@RequestMapping(path="/main", method=RequestMethod.POST)
	public String addLibrarian(
			@Valid User user,
			BindingResult result,
			Model model){
		
		log.info("---------Adding Librarian---------");
		if(result.hasErrors()){	
			System.out.println("Validation error in librariansController");
			return "main";
		} 
		if(userService.isExistedEmail(user)){
			result.rejectValue("email", "duplicate.email");
			return "login";	
		} 
		if(userService.isExistedName(user)){
			result.rejectValue("name", "duplicate.name");
			return "login";	
		}
		
		user.setRole(UserRole.LIBRARIAN);
		user.setEnabled(true);
		Integer librarianId = userService.createUser(user);
		if(librarianId != null) {
			ApplicationEvent event = eventBuilder.createNewListenerWasAddedEvent(user);
			eventPublisher.publish(event);
			
			model.addAttribute("newLibrarianAdded", true);
		} else {
			//another event
		}
		return "redirect:/main";
	}
		
}
