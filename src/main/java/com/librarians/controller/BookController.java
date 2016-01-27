package com.librarians.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import com.librarians.model.entity.Book;
import com.librarians.model.entity.Category;
import com.librarians.model.service.SearchCriteria;
import com.librarians.model.service.UserRole;
import com.librarians.service.Author;
import com.librarians.service.AuthorService;
import com.librarians.service.book.BookService;

@Controller
public class BookController {
	
	static Logger log = Logger.getLogger(BookController.class.getName());

	@Autowired
	private BookService bookService;
	
	@Autowired
	private AuthorService authorService;
	
	@ModelAttribute("authorsList")
	public List<Author> populateAuthorsList(){
		return authorService.getAuthorsList();
	}
	
	@RequestMapping(path="/authors", method=RequestMethod.GET, produces="application/json")
	public @ResponseBody Map<String, Object> addAuthor(@RequestParam String authorName){
		
		try{
			authorService.addAuthor(authorName);
		} catch(HttpClientErrorException ex){
			System.out.println("ERROR----------------------------------"+ex.getMessage());
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isAuthorAdded", true);
		return result;
	}
		
	@RequestMapping(path="/addBook", method=RequestMethod.GET)
	public String showAddBookPage(Model map){
		
		List<Category> categories = bookService.getAllCategories();
		map.addAttribute("categories", categories);
		
		map.addAttribute("book", new Book());
		return "book/newBookForm";
	}

	@RequestMapping(path="/editBook", method=RequestMethod.GET)
	public String showBookEditPage(
			@RequestParam Integer bookId,
			Model map,
			ModelAndView mv){
		
		if(bookId != null){
			Book book = bookService.getBook(bookId);
			map.addAttribute("book", book);
			
			List<Category> categories = bookService.getAllCategories();
			map.addAttribute("categories", categories);
			
			return "book/editBookForm";
		}
		return "main";
	}
	
	@RequestMapping(path="/editBook", method=RequestMethod.POST)
	public String editBook(
			@Valid Book book,
			BindingResult result,
			Model map){
		
		if(!result.hasErrors()){	
			bookService.saveBook(book);
			map.addAttribute("isBookEdited", true);
		} else {
			System.out.println("Book entity has validaion errors " + result.getFieldError().getField());
		}
		return "main";
	}
	
	
	@RequestMapping(path="/newBook", method=RequestMethod.POST)
	public String addBook(
			@ModelAttribute(value="book") @Valid Book book,
			BindingResult result, 
			@RequestParam Integer instanceCount,
			Model map){
		
		if(result.hasErrors()){	
			System.out.println("Book entity has validaion errors " + result.getFieldError().getField());
		} else if(bookService.exist(book)){
			result.rejectValue("isbn", "duplicate.isbn");
			System.out.println("Validation - isbn is already exist");
		} else{
			if(instanceCount == null) {
				instanceCount = 0;
			}		
			book.getCategory().addBook(book);
			bookService.addBook(book, instanceCount);
			map.addAttribute("isBookAdded",true);
		}

		List<Category> categories = bookService.getAllCategories();
		map.addAttribute("categories", categories);
		return "book/newBookForm";
	}
	
	@RequestMapping(path="/bookPage", method=RequestMethod.GET, produces="application/json" )
	public @ResponseBody Map<String, Object> showBookListInTable(
			@RequestParam Integer limit,
			@RequestParam Integer offset,
			@RequestParam(required=false) String order,
			@RequestParam(required=false) String sort,
			@RequestParam(required=false) String search	) {
		
		Long totalItems = bookService.getBookCount(search);
		
		//SearchCriteria is a value object = one time created (no setters) and compare by values(fields)
		SearchCriteria criteria = new SearchCriteria(offset, limit, order, sort, search); 
		List<Book> list = bookService.search(criteria);
		
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("total", totalItems);
		result.put("rows", list);
		
		return result;
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
	
}