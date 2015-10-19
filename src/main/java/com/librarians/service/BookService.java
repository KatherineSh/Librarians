package com.librarians.service;

import com.librarians.model.Book;

public interface BookService {
	
	public void addBook(Book book);

	public boolean exist(Book book);
	
}
