package com.librarians.dao;

import java.util.List;

import com.librarians.model.Book;

public interface BookDao {
	
	public void addBook(Book book);

	public boolean isBookExistBy(Long isbn);

	public List<Book> getList();

	public Long getBookCount();

	public List<Book> getBooksForPage(Integer offset, Integer limit, String sort, String sortField);
}
