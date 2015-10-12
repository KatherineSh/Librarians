package com.librarians.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.librarians.dao.UserService;
import com.librarians.model.User;
import com.librarians.model.UserRole;

@Controller
public class MainController {

	static Logger log = Logger.getLogger(MainController.class.getName());
	
	@Autowired
	private UserService userService;

	@RequestMapping("/main")
	public String showLoginPage(Model map){
		
		log.info("---------Going to main page---------");

		//String email = userService.findEmailByUserName("Vladylen");
		//map.addAttribute("userEmail", email);
		map.addAttribute("user", new User());
		return "main";
	}
	
	@RequestMapping(path="/main", method=RequestMethod.POST)
	public String addLibrarian(
			@Valid User user,
			BindingResult result,
			Model model){
		
		log.info("---------Adding Librarian---------");
		if(result.hasErrors()){	
			return "main";
		}
		
		user.setRole(UserRole.LIBRARIAN);
		Integer userId = userService.addLibrarian(user);
		model.addAttribute("newLibrarianId", userId);
		return "main";
	}
	
}
