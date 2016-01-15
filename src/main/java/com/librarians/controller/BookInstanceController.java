package com.librarians.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.librarians.model.entity.BookInstance;
import com.librarians.service.BookService;

@Controller
public class BookInstanceController {
	
	static Logger log = Logger.getLogger(BookInstanceController.class.getName());

	@Autowired
	private BookService bookService;


	@RequestMapping(path="/book/instances", method=RequestMethod.GET )
	public String getAllBookInstances(Model map, @RequestParam Integer bookId){
		
		List<BookInstance> instances = bookService.getBookInstances(bookId);
		map.addAttribute("instances", instances);
		map.addAttribute("bookId", bookId);
		return "book/instanceList";
	}

	
	@RequestMapping(path="/assignBookToUser", method=RequestMethod.GET, produces="application/json" )
	public @ResponseBody  Map<String, Object> assignBookToUser(Principal principal, @RequestParam Integer bookId){

		String currentUserName = principal.getName();
		
		boolean isAssigned = bookService.assignBookToUser(bookId, currentUserName);
		Integer bookInstancesLeft = bookService.getBookInstancesLeftToAssign(bookId);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isAssigned", isAssigned);
		result.put("bookCountLeft", bookInstancesLeft);
		
		return result;
	}
	
	@RequestMapping(path="/isReturnBookAvailable", method=RequestMethod.GET, produces="application/json" )
	public @ResponseBody  Map<String, Object> checkIsBookAssignedToUser(Principal principal, @RequestParam Integer bookId){
		String currentUserName = principal.getName();
		Map<String, Object> result = new HashMap<String, Object>();
		
		boolean shouldToShow = bookService.isBookAssignedToCurrentUser(bookId, currentUserName);
		result.put("isReturnBookAvailable", shouldToShow);
		return result;
	}
	
	@RequestMapping(path="/returnBook", method=RequestMethod.GET, produces="application/json" )
	public @ResponseBody  Map<String, Object> returnBookInstance(Principal principal, @RequestParam Integer bookId){

		String currentUserName = principal.getName();
		Map<String, Object> result = new HashMap<String, Object>();
		
		boolean isMoreBookInstanceLeft = bookService.returnBook(bookId, currentUserName);
				
		result.put("isMoreBookInstanceLeft", isMoreBookInstanceLeft);
		return result;
	}
}
