package com.librarians.service;

import java.util.List;
import java.util.Map;

import com.librarians.model.Book;
import com.librarians.model.Category;

public interface BookService {
	
	public void addBook(Book book, Integer instanceCount);

	public boolean exist(Book book);
	
	public Long getBookCount(String search);

	public List<Book> list();

	public List<Book> listPage(Integer offset, Integer limit, String sort, String sortField, String search);

	public Map<Integer, Integer> getBookInstances(List<String> booksIdArray);

	public boolean assignBookToUser(Integer bookId, String userName);
	
	public Integer getBookInstancesLeftToAssign(Integer bookId);
	
	public boolean isBookAssignedToCurrentUser(Integer bookId, String currentUerName);

	public boolean returnBook(Integer bookId, String currentUerName);

	public List<Category> getAllCategories();

	public boolean addCategory(Category category);

	public boolean isCategoryExisted(String name);
	
	public Book getBookDetails(Integer id);

	public void changeBookDetails(Book book);

}
