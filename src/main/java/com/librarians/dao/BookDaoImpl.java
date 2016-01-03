package com.librarians.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.librarians.model.Book;
import com.librarians.model.BookCategory;
import com.librarians.model.BookInstance;
import com.librarians.model.User;

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
		Criteria criteria = getSession().createCriteria(Book.class);

		if (order != null && sortField != null) {
			Order sortOrder = (order.equals("asc")) ? Order.asc(sortField) : Order.desc(sortField);
			criteria.addOrder(sortOrder);
		}
		@SuppressWarnings("unchecked")
		List<Book> list = criteria.setFirstResult(offset).setMaxResults(limit).list();
		return list;
	}

	@Transactional
	public List<Book> searchBookBy(String search) {
		Criteria criteria = getSession().createCriteria(Book.class);

		Short year = null;
		try {
			year = Short.parseShort(search);
		} catch (NumberFormatException exception) {
			log.info("Search text string can't be casted to number");
		}

		Long isbn = null;
		try {
			isbn = Long.parseLong(search);
		} catch (NumberFormatException exception) {
			log.info("Search text string can't be casted to number");
		}

		search = '%' + search + '%';

		System.out.println("--------------------------");
		System.out.println("year=" + year);
		System.out.println("isbn = " + isbn);
		System.out.println("title = " + search);

		Junction restriction = Restrictions.disjunction().add(Restrictions.like("title", search))
				.add(Restrictions.like("description", search)).add(Restrictions.like("author", search));

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

	@Transactional
	public Integer getInstanceCountById(Integer id) {

		Criteria criteria = getSession().createCriteria(Book.class, "book");
		criteria.createAlias("book.instances", "instances");

		Junction restriction = Restrictions.conjunction().add(Restrictions.eq("book.id", id))
				.add(Restrictions.eq("instances.status", true));

		Long count = (Long) criteria.add(restriction).setProjection(Projections.rowCount()).uniqueResult();
		return (int) (long) count;
	}

	@Transactional
	public Integer getFreeInstanceCountById(Integer id) {

		Criteria criteria = getSession().createCriteria(Book.class, "book");
		criteria.createAlias("book.instances", "instances");

		Junction restriction = Restrictions.conjunction().add(Restrictions.eq("book.id", id))
				.add(Restrictions.eq("instances.status", true)).add(Restrictions.isNull("instances.user.id"));

		Long count = (Long) criteria.add(restriction).setProjection(Projections.rowCount()).uniqueResult();
		return (int) (long) count;
	}

	@Transactional(rollbackFor = Exception.class)
	public void addBookInstanceToUser(Integer bookId, String userName) throws Exception {
		// update book_instance set user_id = (select id from user where name = "user2") where book_id = "27" and user_id = null
		// DetachedCriteria userId = DetachedCriteria.forClass(User.class).add(Restrictions.eq("name", userName));

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

	@Transactional
	public boolean removeUserAssignmentFromBookInstance(Integer bookId, String userName) {
		
		Session currentSession = getSession();
		boolean isMoreBookInstanceLeft = false;
		
		@SuppressWarnings("unchecked")
		List<BookInstance> takenInstances = (List<BookInstance>) currentSession.createCriteria(BookInstance.class, "bi")
				.createAlias("bi.book", "book").add(Restrictions.eq("book.id", bookId))
				.createAlias("bi.user", "user").add(Restrictions.eq("user.name", userName))
				.list();
		
		if(!takenInstances.isEmpty()){
			if(takenInstances.size() > 1){
				isMoreBookInstanceLeft = true;
			}
			
			BookInstance instance = takenInstances.get(0);
			instance.setUser(null);
			currentSession.update(instance);			
		} 
		
		return isMoreBookInstanceLeft;
	}

	@Transactional
	public List<BookCategory> getAllCategories() {
		
		@SuppressWarnings("unchecked")
		List<BookCategory> categories = (List<BookCategory>) getSession().createCriteria(BookCategory.class).list();
		return categories;
	}

	@Transactional
	public boolean addBookCategory(BookCategory newCategory) {
		
		Session session = getSession();
		session.saveOrUpdate(newCategory);
		
		BookCategory category = (BookCategory) session.createCriteria(BookCategory.class)
				.add(Restrictions.eq("categoryName", newCategory.getCategoryName())).uniqueResult();
		
		return (category != null) ? true : false;
	}

	@Transactional
	public boolean isBookCagtegoryExisted(String categoryName) {
		Long result = (Long) getSession().createCriteria(BookCategory.class)
				.add(Restrictions.eq("categoryName", categoryName))
				.setProjection(Projections.rowCount()).uniqueResult();
		return (result > 0 ) ? true : false;
	}
}
