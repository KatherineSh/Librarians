package com.librarians.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.librarians.model.Book;

@Repository("bookDao")
public class BookDaoImpl extends AbstractDao implements BookDao {
	
	static Logger log = Logger.getLogger(BookDaoImpl.class.getName());


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
	public List<Book> getLimitedAndSortedList(Integer offset, Integer limit, String order, String sortField) {
		Criteria criteria =  getSession().createCriteria(Book.class);
		
		if(order != null && sortField != null){
			Order sortOrder = (order.equals("asc")) ? Order.asc(sortField) : Order.desc(sortField);
			criteria.addOrder(sortOrder);
		}
		@SuppressWarnings("unchecked")
		List<Book> list = criteria.setFirstResult(offset).setMaxResults(limit).list();
		return list;
	}

	@Transactional
	public List<Book> searchBookBy(String search) {
		Criteria criteria =  getSession().createCriteria(Book.class);
		
		Short year = null;
		try {
			year = Short.parseShort(search);
		} catch (NumberFormatException exception) {
			log.info("Search text string can't be casted to number");
		}
		
		Long isbn = null; 
		try {
			isbn = Long.parseLong(search);
		} catch (NumberFormatException exception){
			log.info("Search text string can't be casted to number");
		}
		
		search = '%' + search + '%';
		
		System.out.println("--------------------------");
		System.out.println("year=" + year);
		System.out.println("isbn = " + isbn);
		System.out.println("title = " + search);
		
		Junction restriction = Restrictions.disjunction()
				.add(Restrictions.like("title", search))
				.add(Restrictions.like("description", search))
				.add(Restrictions.like("author", search));
				
		if (year != null) {
			restriction.add(Restrictions.like("year", year));
		} 
		if (isbn != null) {
			restriction.add(Restrictions.like("isbn", isbn));
		}
		criteria.add(restriction);
		
		@SuppressWarnings("unchecked")
		List<Book> list = criteria.list();
		return list;
	}

}
