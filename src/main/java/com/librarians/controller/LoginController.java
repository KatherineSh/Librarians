package com.librarians.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	static Logger log = Logger.getLogger(LoginController.class.getName());

	@RequestMapping(path="/login")
	public String login(){
		
		log.info("---------Show login page---------");
		
		return "login";
	}
}
