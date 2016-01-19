package com.librarians.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.librarians.dao.BookDao;
import com.librarians.dao.BookInstanceDao;
import com.librarians.model.SearchCriteria;
import com.librarians.model.entity.Book;
import com.librarians.model.entity.BookHistory;
import com.librarians.model.entity.BookInstance;
import com.librarians.model.entity.Category;

@Service("bookService")
public class BookServiceImpl implements BookService {
	

	@Autowired
	private BookDao bookDao;
	
	@Autowired
	private BookInstanceDao bookInstanceDao;

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
	public Long getBookCount(String search) {
		return bookDao.getBookCount(search);
	}

	@Override
	public List<Book> search(SearchCriteria criteria) {
		return bookDao.getLimitedAndSortedList(criteria);
	}

	@Override
	public Map<Integer, Integer> getBookInstances(List<String> booksIdArray) {
		Map<Integer, Integer> result = new HashMap<Integer,Integer>();
		
		if(!booksIdArray.isEmpty()){
			for(String id : booksIdArray){
				int value = Integer.parseInt(id);
				Integer count = bookInstanceDao.getFreeInstanceCountById(value);
				result.put(value, count);
			}
		}
		return result;
	}

	@Override
	public boolean assignBookToUser(Integer bookId, String userName) {
		try {
			bookInstanceDao.addBookInstanceToUser(bookId, userName);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Integer getBookInstancesLeftToAssign(Integer bookId) {
		Integer count = bookInstanceDao.getFreeInstanceCountById(bookId);
		return count;
	}

	@Override
	public boolean isBookAssignedToCurrentUser(Integer bookId, String userName) {
		boolean isAssigned = bookInstanceDao.isBookAssignedToUser(bookId, userName);
		return isAssigned;
	}

	@Override
	public boolean returnBook(Integer bookId, String userName) {
		try {
			bookInstanceDao.removeUserAssignmentFromBookInstance(bookId, userName);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Category> getAllCategories() {
		return bookDao.getAllCategories();
	}

	@Override
	public boolean addCategory(Category newCategory) {
		return bookDao.addBookCategory(newCategory);
	}

	@Override
	public boolean isCategoryExisted(String categoryName) {
		return bookDao.isBookCagtegoryExisted(categoryName);
	}

	@Override
	public Book getBook(Integer id) {
		return bookDao.getBook(id);
	}

	@Override
	public void saveBook(Book book) {	
		bookDao.saveBook(book);
	}

	@Override
	public List<BookHistory> getBookInstanceHistory(Integer bookInstanceId) {
		return bookInstanceDao.getBookInstanceHistory(bookInstanceId);
	}

	@Override
	public List<BookInstance> getBookInstances(Integer bookId) {
		return bookInstanceDao.getBookInstances(bookId);
	}

	@Override
	public boolean isMoreAvailableToReturn(Integer bookId, String currentUserName) {
		return bookInstanceDao.getTakenInstancesCount(bookId, currentUserName);
	}
}
