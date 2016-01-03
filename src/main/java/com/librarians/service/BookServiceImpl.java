package com.librarians.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.librarians.dao.BookDao;
import com.librarians.model.Book;
import com.librarians.model.BookCategory;
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

	@Override
	public Map<Integer, Integer> getBookInstances(List<String> booksIdArray) {
		Map<Integer, Integer> result = new HashMap<Integer,Integer>();
		
		if(!booksIdArray.isEmpty()){
			for(String id : booksIdArray){
				int value = Integer.parseInt(id);
				Integer count = bookDao.getFreeInstanceCountById(value);
				result.put(value, count);
			}
		}
		return result;
	}

	@Override
	public boolean assignBookToUser(Integer bookId, String userName) {
		try {
			bookDao.addBookInstanceToUser(bookId, userName);
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Integer getBookInstancesLeftToAssign(Integer bookId) {
		Integer count = bookDao.getFreeInstanceCountById(bookId);
		return count;
	}

	@Override
	public boolean isBookAssignedToCurrentUser(Integer bookId, String userName) {
		boolean isAssigned = bookDao.isBookAssignedToUser(bookId, userName);
		return isAssigned;
	}

	@Override
	public boolean returnBook(Integer bookId, String userName) {
		boolean isMoreBookInstanceLeft = bookDao.removeUserAssignmentFromBookInstance(bookId, userName);
		return isMoreBookInstanceLeft;
	}

	@Override
	public List<BookCategory> getAllBookCategories() {
		return bookDao.getAllCategories();
	}

	@Override
	public boolean addBookCategory(BookCategory newCategory) {
		return bookDao.addBookCategory(newCategory);
	}

	@Override
	public boolean isCategoryExisted(String categoryName) {
		return bookDao.isBookCagtegoryExisted(categoryName);
	}
}
