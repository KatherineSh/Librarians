package com.librarians.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.librarians.model.Book;
import com.librarians.model.BookInstance;
import com.librarians.service.BookService;

@Controller
public class BookController {
	
	static Logger log = Logger.getLogger(BookController.class.getName());

	@Autowired
	private BookService bookService;
	
	@RequestMapping(path="/newBook", method=RequestMethod.POST)
	public String addBook(@Valid Book book, BindingResult result, @RequestParam Integer instanceCount){
		
		if(result.hasErrors()){	
			System.out.println("Book entity has validaion errors " + result.getFieldError().getField());
			return "book/newBookForm";
		}else if(bookService.exist(book)){
			result.rejectValue("isbn", "duplicate.isbn");
			System.out.println("Validation - isbn is already exist");
			return "book/newBookForm";	
		}
		if(instanceCount == null) {
			instanceCount = 0;
		}		
		bookService.addBook(book, instanceCount);
		return "redirect:/addBook";
	}
	
	@RequestMapping(path="/bookPage", method=RequestMethod.GET, produces="application/json" )
	public @ResponseBody Map<String, Object> showBookListInTable(
			 
			@RequestParam Integer limit,
			@RequestParam Integer offset,
			@RequestParam(required=false) String order,
			@RequestParam(required=false) String sort,
			
			@RequestParam(required=false) String search) {
												
		Long totalItems = bookService.getBookCount();
		List<Book> list = bookService.listPage(offset, limit, order, sort);
		
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("total", totalItems);
		result.put("rows", list);
		
		return result;
	}
	
	@RequestMapping(path="/searchBook", method=RequestMethod.GET, produces="application/json" )
	public @ResponseBody Map<String, Object> searchInBookTable(
			
			@RequestParam String search){		
		
		List<Book> resultList = bookService.searchBookBy(search);
		Integer totalResults = resultList.size();
		
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("total", totalResults);
		result.put("rows", resultList);
		
		return  result;
	}
	
}
