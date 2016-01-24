package com.librarians.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.librarians.model.entity.BookHistory;
import com.librarians.model.entity.User;
import com.librarians.model.service.ReaderHistory;
import com.librarians.model.service.UserProfile;
import com.librarians.service.book.BookService;
import com.librarians.service.user.UserService;

@Controller
public class BookHistoryController {
	
	static Logger log = Logger.getLogger(BookHistoryController.class.getName());

	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserService userservice; 

	@Autowired
	private ReaderHistory readerHistory;
	
	@RequestMapping(value = "/book/history", method = RequestMethod.GET)
	public String getBookHistory(@RequestParam Integer bookId, @RequestParam Integer bookInstanceId, Model map){
		
		List<BookHistory> bookHistory = bookService.getBookInstanceHistory(bookInstanceId);
		map.addAttribute("historyList", bookHistory);
		map.addAttribute("bookId", bookId); //pass it to make back URL available
		return "book/history/bookHistory";
	}
	
	@RequestMapping(value = "/book/history/currentUser", method = RequestMethod.GET)
	public String getUserBookHistory(Model map){
		
		UserProfile profile = userservice.getUserProfile();
		User currentReader = profile.getUser();
		if(currentReader != null){
						 
			List<BookHistory> history= readerHistory.getBookHistoryForUSer(currentReader.getId());
			map.addAttribute("historyList", history);
		}
		return "book/history/readerHistory";
	}
}
