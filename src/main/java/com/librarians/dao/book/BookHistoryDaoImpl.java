package com.librarians.dao.book;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.librarians.dao.AbstractDao;
import com.librarians.model.entity.BookHistory;

@Repository("bookHistoryDao")
public class BookHistoryDaoImpl extends AbstractDao implements BookHistoryDao {

	@Transactional
	public List<BookHistory> getBookoHistoryForUser(Integer readedrId) {
		
		Criteria criteria = getSession().createCriteria(BookHistory.class, "bh")
			.createAlias("bh.bookInstance", "bi")
			.createAlias("bi.book", "book")
			.createAlias("bh.reader", "reader")
			.add(Restrictions.eq("reader.id", readedrId));
		
		criteria.addOrder(Order.desc("bh.expirationDate"));
		
		@SuppressWarnings("unchecked")
		List<BookHistory> result = criteria.list();
		
		return result;
	}

	
}
