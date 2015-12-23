package com.librarians.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.librarians.model.Book;
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
			@RequestParam(required=false) String search
	) {
												
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
	
	@RequestMapping(path="/checkBooksCount", method=RequestMethod.GET, produces="application/json" )
	public @ResponseBody Map<Integer, Integer> checkBooksAvalability(HttpServletRequest req ){
			//@RequestParam Map<String, String[]> books){
		
		String[] books = req.getParameterValues("books[]");	
		Map<Integer,Integer> map = bookService.getBookInstances(Arrays.asList(books));
		
		//Map<String, Object> result = new HashMap<String, Object>();
		//result.put("bookAvailability", map);
		return map;
		
	}
	
	@RequestMapping(path="/assignBookToUser", method=RequestMethod.GET, produces="application/json" )
	public @ResponseBody  Map<String, Object> assignBookToUser( HttpServletRequest request, @RequestParam Integer bookId){
		
		SecurityContext context = SecurityContextHolder.getContext();
		Object principal = context.getAuthentication().getPrincipal();
		String userName = "";
		if(principal instanceof org.springframework.security.core.userdetails.User) {
			userName = ((org.springframework.security.core.userdetails.User) principal).getUsername();
		}
		
		boolean isAssigned = bookService.assignBookToUser(bookId, userName);
		Integer bookInstancesLeft = bookService.getBookInstancesLeftToAssign(bookId);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isAssigned", isAssigned);
		result.put("bookCountLeft", bookInstancesLeft);
		
		return result;
	}
}
