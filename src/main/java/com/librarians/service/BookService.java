package com.librarians.service;

import java.util.List;

import com.librarians.model.Book;

public interface BookService {
	
	public void addBook(Book book);

	public boolean exist(Book book);

	public List<Book> list();

	public Long getBookCount();

	public List<Book> listPage(Integer offset, Integer limit, String sort, String sortField);

	public List<Book> searchBookBy(String search);
	
}
