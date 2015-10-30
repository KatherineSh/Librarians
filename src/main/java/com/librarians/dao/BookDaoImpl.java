package com.librarians.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
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
		Long result = (Long) getSession().createCriteria(Book.class).add(Restrictions.eq("isbn", isbn))
				.setProjection(Projections.rowCount()).uniqueResult();
		return (result > 0) ? true : false;
	}

	@Transactional
	public List<Book> getList() {
		@SuppressWarnings("unchecked")
		List<Book> bookList = getSession().createCriteria(Book.class).list();
		return bookList;
	}

	@Transactional
	public Long getBookCount() {
		Criteria criteria = getSession().createCriteria(Book.class);
		criteria.setProjection(Projections.rowCount());
		Long count = (Long) criteria.uniqueResult();
		return count;
	}

	@Transactional
	public List<Book> getBooksForPage(Integer offset, Integer limit, String order, String sortField) {
		Criteria criteria =  getSession().createCriteria(Book.class);
		
		if(order != null && sortField != null){
			Order sortOrder = (order.equals("asc")) ? Order.asc(sortField) : Order.desc(sortField);
			criteria.addOrder(sortOrder);
		}
		@SuppressWarnings("unchecked")
		List<Book> list = criteria.setFirstResult(offset).setMaxResults(limit).list();
		return list;
	}

}
