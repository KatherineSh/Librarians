package com.librarians.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.librarians.model.entity.Book;
import com.librarians.model.entity.BookHistory;
import com.librarians.model.entity.BookInstance;
import com.librarians.model.entity.BookState;
import com.librarians.model.entity.User;

@Repository("bookInstanceDao")
public class BookInstanceDaoImpl extends AbstractDao implements BookInstanceDao {

	@Transactional
	public Integer getInstanceCountById(Integer id) {

		Criteria criteria = getSession().createCriteria(Book.class, "book");
		criteria.createAlias("book.instances", "instances");

		Junction restriction = Restrictions.conjunction().add(Restrictions.eq("book.id", id))
				.add(Restrictions.eq("instances.active", true));

		Long count = (Long) criteria.add(restriction).setProjection(Projections.rowCount()).uniqueResult();
		return (int) (long) count;
	}

	@Transactional
	public Integer getFreeInstanceCountById(Integer id) {

		Criteria criteria = getSession().createCriteria(Book.class, "book");
		criteria.createAlias("book.instances", "instances");

		Junction restriction = Restrictions.conjunction().add(Restrictions.eq("book.id", id))
				.add(Restrictions.eq("instances.active", true)).add(Restrictions.isNull("instances.user.id"));

		Long count = (Long) criteria.add(restriction).setProjection(Projections.rowCount()).uniqueResult();
		return (int) (long) count;
	}

	@Transactional(rollbackFor = Exception.class)
	public void addBookInstanceToUser(Integer bookId, String userName) throws Exception {
		Session session = getSession();
		User user = (User) session.createCriteria(User.class).add(Restrictions.eq("name", userName)).uniqueResult();

		@SuppressWarnings("unchecked")
		List<BookInstance> list = (List<BookInstance>) session.createCriteria(BookInstance.class, "bi")
				.createAlias("bi.book", "book").add(Restrictions.eq("bi.book.id", bookId))
				.add(Restrictions.isNull("bi.user.id")).list();

		if (list.isEmpty()) {
			throw new Exception();
		}
		BookInstance instanceToAssign = list.get(0);
		instanceToAssign.setUser(user);
		session.saveOrUpdate(instanceToAssign);
		
		Calendar expiredDate = Calendar.getInstance();
		expiredDate.setTime(new Date());
		expiredDate.add(Calendar.DATE, 30);
		BookHistory history = new BookHistory(instanceToAssign, user, BookState.IN_TAKE, new Date(), expiredDate.getTime());
		session.save(history);
	}

	@Transactional
	public boolean isBookAssignedToUser(Integer bookId, String userName) {

		@SuppressWarnings("unchecked")
		List<BookInstance> takenInstances = (List<BookInstance>) getSession().createCriteria(BookInstance.class, "bi")
				.createAlias("bi.book", "book").add(Restrictions.eq("book.id", bookId))
				.createAlias("bi.user", "user").add(Restrictions.eq("user.name", userName))
				.list();

		if (!takenInstances.isEmpty()) {
			return true;
		}
		return false;
	}

	@Transactional(rollbackFor = Exception.class)
	public void removeUserAssignmentFromBookInstance(Integer bookId, String userName) throws Exception {
		
		Session currentSession = getSession();
		//boolean isMoreBookInstanceLeft = false;
		
		BookInstance takenInstance = (BookInstance) currentSession.createCriteria(BookInstance.class, "bi")
				.createAlias("bi.book", "book").add(Restrictions.eq("book.id", bookId))
				.createAlias("bi.user", "user").add(Restrictions.eq("user.name", userName))
				.setMaxResults(1).uniqueResult();

		BookHistory historyToChange = (BookHistory) currentSession.createCriteria(BookHistory.class, "bh")
				.createAlias("bh.bookInstance", "instance").add(Restrictions.eq("instance.id", takenInstance.getId()))
				.createAlias("bh.reader", "reader").add(Restrictions.eq("reader.name", userName))
				.add(Restrictions.eq("state", BookState.IN_TAKE)).uniqueResult();		
		historyToChange.setDateWhenReturned(new Date());
		historyToChange.setState(BookState.RETURNED);
		
		takenInstance.setUser(null);
		currentSession.update(takenInstance);
	}


	@Transactional
	public boolean getTakenInstancesCount(Integer bookId, String currentUserName) {
		Long countLeft = (Long)  getSession().createCriteria(BookInstance.class, "bi")
				.createAlias("bi.book", "book").add(Restrictions.eq("book.id", bookId))
				.createAlias("bi.user", "user").add(Restrictions.eq("user.name", currentUserName))
				.setProjection(Projections.rowCount()).uniqueResult();
		return (countLeft > 0) ? true : false;
	}

	@Transactional
	public List<BookHistory> getBookInstanceHistory(Integer bookInstanceId) {

		@SuppressWarnings("unchecked")
		List<BookHistory> historyList = getSession().createCriteria(BookHistory.class).add(Restrictions.eq("bookInstance.id", bookInstanceId)).list();
				
		for(BookHistory history : historyList){
			Hibernate.initialize(history.getReader());
		}
		return historyList;
	}

	@Transactional
	public List<BookInstance> getBookInstances(Integer bookId) {
		Book book = (Book) getSession().get(Book.class, bookId);
		book.getInstances().size();

		List<BookInstance> result = new ArrayList<BookInstance>(book.getInstances());
		return result; 
	}

}
