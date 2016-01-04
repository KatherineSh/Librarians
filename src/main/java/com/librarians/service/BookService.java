package com.librarians.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.librarians.model.Book;
import com.librarians.model.Category;

public interface BookService {
	
	public void addBook(Book book, Integer instanceCount);

	public boolean exist(Book book);

	public List<Book> list();

	public Long getBookCount();

	public List<Book> listPage(Integer offset, Integer limit, String sort, String sortField);

	public List<Book> searchBookBy(String search);

	public Map<Integer, Integer> getBookInstances(List<String> booksIdArray);

	public boolean assignBookToUser(Integer bookId, String userName);
	
	public Integer getBookInstancesLeftToAssign(Integer bookId);
	
	public boolean isBookAssignedToCurrentUser(Integer bookId, String currentUerName);

	public boolean returnBook(Integer bookId, String currentUerName);

	public List<Category> getAllCategories();

	public boolean addCategory(Category category);

	public boolean isCategoryExisted(String name);
}
