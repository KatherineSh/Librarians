package com.librarians.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.librarians.model.User;
import com.librarians.model.UserRole;
import com.librarians.service.UserService;

@Controller
public class UserRegistrationController {
	
	@Autowired
	private UserService userService;
	
	static Logger log = Logger.getLogger(UserRegistrationController.class.getName());

	@RequestMapping(path="/register", method=RequestMethod.POST)
	public String registerUser(@Valid User user, BindingResult result, Model model, RedirectAttributes redirectAttrs){
		
		if(result.hasErrors()) {
			System.out.println("Validation errors in registration user form");
			return "login";
		} else if(userService.exist(user)){
			result.rejectValue("email", "duplicate.email");
			System.out.println("Error during call of exist method");
			return "login";	
		}
		
		userService.addUser(user);
		
		redirectAttrs.addFlashAttribute("newUserCreated", user.getName());
		return "redirect:/login";
	}
}
