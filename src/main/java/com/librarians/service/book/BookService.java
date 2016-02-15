package com.librarians.service.book;

import java.util.List;
import java.util.Map;

import com.librarians.model.entity.Book;
import com.librarians.model.entity.BookHistory;
import com.librarians.model.entity.BookInstance;
import com.librarians.model.entity.Category;
import com.librarians.model.service.SearchCriteria;

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
	
	public Map<Integer, Integer> getBookInstancesCount(List<String> booksIdArray);
	
	public List<BookInstance> getBookInstances(Integer bookId);

	public boolean takeBook(Integer bookId, String userName);
	
	public Integer getBookInstancesFreeToTake(Integer bookId);
	
	public boolean isBookTakenByCurrentUser(Integer bookId, String currentUerName);

	public boolean returnBook(Integer bookId, String currentUerName);
	
	public List<BookHistory> getBookInstanceHistory(Integer bookInstanceId);

	public boolean isMoreAvailableToReturn(Integer bookId, String currentUserName);

}
