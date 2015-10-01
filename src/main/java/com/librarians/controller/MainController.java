package com.librarians.controller;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.librarians.model.User;


@Controller
public class MainController {
	
	@Autowired
	private User user;
	
	static Logger log = Logger.getLogger(MainController.class.getName());

	@RequestMapping("/main")
	public String showLoginPage(Model model){
		
		log.info("---------Going to main page---------");
		
		return "main";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
}
