package com.librarians.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import org.springframework.web.servlet.ModelAndView;

import com.librarians.model.Book;
import com.librarians.model.Category;
import com.librarians.model.UserRole;
import com.librarians.service.BookService;

@Controller
public class BookController {
	
	static Logger log = Logger.getLogger(BookController.class.getName());

	@Autowired
	private BookService bookService;
	
	
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
			Book book = bookService.getBookDetails(bookId);
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
			bookService.changeBookDetails(book);
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
			@RequestParam(required=false) String search
	) {
		
		Long totalItems = bookService.getBookCount(search);
		List<Book> list = bookService.listPage(offset, limit, order, sort, search);
		
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
	
	@RequestMapping(path="/addCategory", method=RequestMethod.GET, produces="application/json" )
	public @ResponseBody Map<String, Object> addCategory(Authentication authentication, @RequestParam String categoryName){
		
		GrantedAuthority auth = new SimpleGrantedAuthority(UserRole.LIBRARIAN.toString());
		boolean isLibrarian = authentication.getAuthorities().contains(auth);

		Map<String, Object> result = new HashMap<String, Object>();
		if (isLibrarian && categoryName != null) {	
			String name = categoryName.trim();
			if(!name.isEmpty()) {
				
				boolean isExisted = bookService.isCategoryExisted(name);
				if(!isExisted){
					Category category = new Category();
					category.setCategoryName(name);
					boolean isCategoryAdded = bookService.addCategory(category);			
					result.put("isCategoryAdded", isCategoryAdded);
					
					List<Category> categories = bookService.getAllCategories();
					result.put("updatedCategories", categories);
				} else {
					result.put("isCategoryAdded", false);
					result.put("isCategoryExisted", true);
				}
			} else {
				result.put("isCategoryAdded", false);
			}
		}
		return result;
	}
}