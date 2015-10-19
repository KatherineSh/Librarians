package com.librarians.dao;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.librarians.model.Book;

@Repository("bookDao")
public class BookDaoImpl extends AbstractDao implements BookDao {

	@Transactional
	public void addBook(Book book) {
		getSession().save(book);
	}

	@Transactional
	public boolean isBookExistBy(Long isbn) {
		Long result = (Long) getSession().createCriteria(Book.class).add(Restrictions.eq("isbn", isbn)).setProjection(Projections.rowCount())
				.uniqueResult();
		return (result > 0) ? true : false;
	}
}
