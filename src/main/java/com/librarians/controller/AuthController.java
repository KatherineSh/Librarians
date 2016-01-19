package com.librarians.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.librarians.model.entity.User;

@Controller
public class AuthController {
	
	static Logger log = Logger.getLogger(AuthController.class.getName());

	@RequestMapping(path="/login", method=RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error){
		
		log.info("---------Show login page---------");
		
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username or password!");
			log.info("---------Invalid username or password!---------");
		}
		
		model.addObject("user", new User());
		model.setViewName("login");
		System.out.println("Auth controller");
		/*SecurityContext context = SecurityContextHolder.getContext();
		Object principal = context.getAuthentication().getPrincipal();*/
		return model;
	}



}
