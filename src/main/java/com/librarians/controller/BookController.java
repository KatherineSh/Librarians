package com.librarians.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.librarians.model.Book;
import com.librarians.service.BookService;

@Controller
public class BookController {
	
	static Logger log = Logger.getLogger(BookController.class.getName());

	@Autowired
	private BookService bookService;
	
	@RequestMapping(path="/newBook", method=RequestMethod.POST)
	public String addBook(@Valid Book book, BindingResult result) {
		if(result.hasErrors()){	
			System.out.println("Book entity has errors"+result.getFieldError().getField());
			return "main";
		} else if(bookService.exist(book)){
			result.rejectValue("isbn", "duplicate.isbn");
			System.out.println("Error during call of exist method");
			return "main";	
		}
		bookService.addBook(book);
		return "redirect:/main";
	}
}
