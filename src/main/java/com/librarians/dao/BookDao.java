package com.librarians.dao;

import java.util.List;

import com.librarians.model.SearchCriteria;
import com.librarians.model.entity.Book;
import com.librarians.model.entity.BookHistory;
import com.librarians.model.entity.BookInstance;
import com.librarians.model.entity.Category;

public interface BookDao {
	
	public void addBook(Book book);

	public boolean isBookExistBy(Long isbn);

	public List<Book> getList();

	public Long getBookCount(String search);

	public List<Book> getLimitedAndSortedList(SearchCriteria criteria);

	public Integer getInstanceCountById(Integer id);

	public Integer getFreeInstanceCountById(Integer id);

	public void addBookInstanceToUser(Integer bookId, String userName) throws Exception;

	public boolean isBookAssignedToUser(Integer bookId, String userName);

	public boolean removeUserAssignmentFromBookInstance(Integer bookId, String userName);

	public List<Category> getAllCategories();

	public boolean addBookCategory(Category newCategory);

	public boolean isBookCagtegoryExisted(String categoryName);

	public Book getBook(Integer id);

	public void setBookDetails(Book book);

	public List<BookHistory> getBookInstanceHistory(Integer bookInstanceId);

	public List<BookInstance> getBookInstances(Integer bookId);
}
