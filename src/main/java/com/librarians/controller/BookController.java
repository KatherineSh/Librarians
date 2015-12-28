package com.librarians.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.librarians.model.Book;
import com.librarians.model.UserRole;
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

		Map<Integer,Integer> map = new HashMap<>();
		
		SecurityContext context = SecurityContextHolder.getContext();
		Iterator<? extends GrantedAuthority> auth =  context.getAuthentication().getAuthorities().iterator();
		String role = auth.next().getAuthority();

		
		if (role.equals(UserRole.USER.toString())) {
			String[] books = req.getParameterValues("books[]");
			map = bookService.getBookInstances(Arrays.asList(books));
		}
		return map;
	}
	
	@RequestMapping(path="/assignBookToUser", method=RequestMethod.GET, produces="application/json" )
	public @ResponseBody  Map<String, Object> assignBookToUser(Principal principal, @RequestParam Integer bookId){
		
		/*SecurityContext context = SecurityContextHolder.getContext();
		Object principal = context.getAuthentication().getPrincipal();
		String userName = "";
		if(principal instanceof org.springframework.security.core.userdetails.User) {
			userName = ((org.springframework.security.core.userdetails.User) principal).getUsername();
		}*/
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
