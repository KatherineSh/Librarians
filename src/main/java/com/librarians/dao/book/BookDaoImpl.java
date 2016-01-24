package com.librarians.dao.book;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.librarians.dao.AbstractDao;
import com.librarians.model.entity.Book;
import com.librarians.model.entity.Category;
import com.librarians.model.service.SearchCriteria;

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
	public Long getBookCount(String search) {
		Criteria criteria = getSession().createCriteria(Book.class);
		
		if (search != null && !search.isEmpty()) {
			criteria = addSearch(criteria, search);
		}
		criteria.setProjection(Projections.rowCount());
		Long count = (Long) criteria.uniqueResult();
		return count;
	}

	@Transactional
	public List<Book> getLimitedAndSortedList(SearchCriteria searchCriteria) {
		Criteria criteria = getSession().createCriteria(Book.class, "book");

		if (searchCriteria.hasSearchKey()) {
			criteria = addSearch(criteria, searchCriteria.getSearchKey());
		}
		
		if (searchCriteria.hasOrderAndSortField()) {
			Order order = null;
			if(searchCriteria.isOrderByAssosiatedTable()){

				String assosiatedTable = searchCriteria.getAssosiatedTableName();
				criteria.createAlias("book" + "." + assosiatedTable, "c").add(Restrictions.eqProperty("c.id", assosiatedTable + ".id"));
				order = searchCriteria.getOrder("c");
			} else {
				order = searchCriteria.getOrder();
			}
			criteria.addOrder(order);
		}
		criteria.setFirstResult(searchCriteria.getOffset()).setMaxResults(searchCriteria.getLimit());
		
		@SuppressWarnings("unchecked")
		List<Book> list = criteria.list();
		return list;
	}

	private Criteria addSearch(Criteria criteria, String search) {
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

		search = '%' + search.trim() + '%';

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
		return criteria;
	}
	
	@Transactional
	public List<Category> getAllCategories() {
		
		@SuppressWarnings("unchecked")
		List<Category> categories = (List<Category>) getSession().createCriteria(Category.class).list();
		return categories;
	}

	@Transactional
	public boolean addBookCategory(Category newCategory) {
		
		Session session = getSession();
		session.saveOrUpdate(newCategory);
		
		Category category = (Category) session.createCriteria(Category.class)
				.add(Restrictions.eq("categoryName", newCategory.getCategoryName())).uniqueResult();
		
		return (category != null) ? true : false;
	}

	@Transactional
	public boolean isBookCagtegoryExisted(String categoryName) {
		Long result = (Long) getSession().createCriteria(Category.class)
				.add(Restrictions.eq("categoryName", categoryName))
				.setProjection(Projections.rowCount()).uniqueResult();
		return (result > 0 ) ? true : false;
	}

	@Transactional
	public Book getBook(Integer id) {
		Session session = getSession();
		Book book = (Book) session.get(Book.class, id);
		Hibernate.initialize(book);  //to initialize associated entities (lazily fetched)
		book.getInstances().size();  //to set in book instances
		return book;
	}

	@Transactional
	public void saveBook(Book book) {
		Session session = getSession();
		session.update(book); 
	}
}
