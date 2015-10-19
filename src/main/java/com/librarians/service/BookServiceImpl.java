package com.librarians.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.librarians.dao.BookDao;
import com.librarians.model.Book;

@Service("bookService")
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookDao bookDao;

	@Override
	public void addBook(Book book) {
		bookDao.addBook(book);
	}

	@Override
	public boolean exist(Book book) {
		return bookDao.isBookExistBy(book.getIsbn());
	}
}
