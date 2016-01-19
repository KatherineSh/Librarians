package com.librarians.service;

import java.util.List;
import java.util.Map;

import com.librarians.model.SearchCriteria;
import com.librarians.model.entity.Book;
import com.librarians.model.entity.BookHistory;
import com.librarians.model.entity.BookInstance;
import com.librarians.model.entity.Category;

public interface BookService {
	
	public void addBook(Book book, Integer instanceCount);

	public boolean exist(Book book);
	
	public Long getBookCount(String search);

	public List<Book> list();

	public List<Book> search(SearchCriteria criteria);

	public List<Category> getAllCategories();

	public boolean addCategory(Category category);

	public boolean isCategoryExisted(String name);
	
	public Book getBook(Integer id);

	public void saveBook(Book book);

	//instances
	//TODO move to another service
	
	public Map<Integer, Integer> getBookInstances(List<String> booksIdArray);
	
	public List<BookInstance> getBookInstances(Integer bookId);

	public boolean assignBookToUser(Integer bookId, String userName);
	
	public Integer getBookInstancesLeftToAssign(Integer bookId);
	
	public boolean isBookAssignedToCurrentUser(Integer bookId, String currentUerName);

	public boolean returnBook(Integer bookId, String currentUerName);
	
	public List<BookHistory> getBookInstanceHistory(Integer bookInstanceId);

	public boolean isMoreAvailableToReturn(Integer bookId, String currentUserName);

}
