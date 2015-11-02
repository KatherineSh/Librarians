package com.librarians.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.librarians.model.Book;
import com.librarians.model.User;

@Controller
public class MainController {

	static Logger log = Logger.getLogger(MainController.class.getName());

	@RequestMapping(path="/main", method=RequestMethod.GET)
	public String showMainPage(HttpServletRequest req, Model map){
		
		log.info("---------Going to main page---------");
		System.out.println(req.getUserPrincipal().toString());
		System.out.println(req.isUserInRole("ADMIN"));
		return "main";
	}
	
	@RequestMapping(path="/addBook", method=RequestMethod.GET)
	public String showAddBookPage(Model map){
		map.addAttribute("book", new Book());
		return "book/newBookForm";
	}
	
	@RequestMapping(path="/addLibrarian", method=RequestMethod.GET)
	public String showAddLibrarianPage(Model map){
		map.addAttribute("user", new User());
		return "librarian/addLibrarianForm";
	}
	
	
	@RequestMapping(path="/userList", method=RequestMethod.GET)
	public String showUserListPage(Model map){	
		return "user/userList";
	}
	
	
	
}
