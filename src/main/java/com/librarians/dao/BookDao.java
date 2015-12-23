package com.librarians.dao;

import java.util.List;

import com.librarians.model.Book;

public interface BookDao {
	
	public void addBook(Book book);

	public boolean isBookExistBy(Long isbn);

	public List<Book> getList();

	public Long getBookCount();

	public List<Book> getLimitedAndSortedList(Integer offset, Integer limit, String sort, String sortField);

	public List<Book> searchBookBy(String search);

	public Integer getInstanceCountById(Integer id);

	public Integer getFreeInstanceCountById(Integer id);

	public void addBookInstanceToUser(Integer bookId, String userName) throws Exception;
}
