package com.librarians.dao;

import com.librarians.model.Book;

public interface BookDao {
	
	public void addBook(Book book);

	public boolean isBookExistBy(Long isbn);
}
