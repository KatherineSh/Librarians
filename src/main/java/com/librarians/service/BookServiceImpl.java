package com.librarians.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.librarians.dao.BookDao;
import com.librarians.model.Book;
import com.librarians.model.BookInstance;

@Service("bookService")
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookDao bookDao;

	@Override
	public void addBook(Book book, Integer instanceCount) {
		Set<BookInstance> instances = new HashSet<BookInstance>();
		while(instanceCount > 0){
			instances.add(new BookInstance(book));
			instanceCount--;
		}
		book.setInstances(instances);
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
		return bookDao.getLimitedAndSortedList(offset,limit, sort, sortField);
	}

	@Override
	public List<Book> searchBookBy(String search) {
		return bookDao.searchBookBy(search);
	}
}
