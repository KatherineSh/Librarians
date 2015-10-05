package com.librarians.controller;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.librarians.dao.UserService;


@Controller
public class MainController {

	
	static Logger log = Logger.getLogger(MainController.class.getName());
	
	@Autowired
	private UserService userService;
	

	@RequestMapping("/main")
	public String showLoginPage(Model map){
		
		log.info("---------Going to main page---------");

		String email = userService.getUserEmailByLogin("vlad");
		map.addAttribute("userEmail", email);
		return "main";
	}
	
}
