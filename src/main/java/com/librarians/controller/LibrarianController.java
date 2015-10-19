package com.librarians.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.librarians.model.User;
import com.librarians.model.UserRole;
import com.librarians.service.UserService;

@Controller
public class LibrarianController {

	static Logger log = Logger.getLogger(LibrarianController.class.getName());
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(path="/main", method=RequestMethod.POST)
	public String addLibrarian(
			@Valid User user,
			BindingResult result,
			Model model){
		
		log.info("---------Adding Librarian---------");
		if(result.hasErrors()){	
			System.out.println("Validation error in librariansController");
			return "main";
		} else if(userService.exist(user)){
			result.rejectValue("email", "duplicate.email");
			System.out.println("Error during call of exist method");
			return "main";	
		}
		
		user.setRole(UserRole.LIBRARIAN);
		userService.addLibrarian(user);
		model.addAttribute("newLibrarianAdded", true);
		return "redirect:/main";
	}
		
}
