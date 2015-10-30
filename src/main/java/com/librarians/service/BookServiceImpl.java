package com.librarians.service;

import java.util.List;

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

	@Override
	public List<Book> list() {
		return bookDao.getList();
	}

	@Override
	public Long getBookCount() {
		return bookDao.getBookCount();
	}

	@Override
	public List<Book> listPage(Integer offset, Integer limit, String sort, String sortField) {
		return bookDao.getBooksForPage(offset,limit, sort, sortField);
	}
}
