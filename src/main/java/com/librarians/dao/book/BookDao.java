package com.librarians.dao.book;

import java.util.List;

import com.librarians.model.entity.Book;
import com.librarians.model.entity.Category;
import com.librarians.model.service.SearchCriteria;

public interface BookDao {
	
	public void addBook(Book book);

	public boolean isBookExistBy(Long isbn);

	public List<Book> getList();

	public Long getBookCount(String search);

	public List<Book> getLimitedAndSortedList(SearchCriteria criteria);

	public List<Category> getAllCategories();

	public boolean addBookCategory(Category newCategory);

	public boolean isBookCagtegoryExisted(String categoryName);

	public Book getBook(Integer id);

	public void saveBook(Book book);
}
