package com.librarians.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.librarians.model.UserRole;
import com.librarians.model.entity.User;

@Component("userDao")
public class UserDaoImpl extends AbstractDao implements UserDao {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	static Logger log = Logger.getLogger(UserDaoImpl.class.getName());
	
	@Transactional
	public String getEmailByUserName(String name) {
		Query query =  getSession().createQuery("select user.email from User as user where user.name = ?");
		query.setString(0, name);
		String email = (String) query.uniqueResult();
		return email;
	}
	
	@Transactional
	public Integer createNewUser(User user) {
		user.setPass(passwordEncoder.encode(user.getPass()));
		
		Integer savedUserId = (Integer) getSession().save(user);
		return savedUserId;
	}

	@Transactional
	public boolean isUserExistedByEmail(String email) {
		Long count = (Long) getSession().createCriteria(User.class)
				.add(Restrictions.eq("email", email))
				.setProjection(Projections.rowCount())
				.uniqueResult();
		return (count > 0) ? true : false;
	}
	
	@Transactional
	public boolean isUserExistedByName(String name) {
		Long count = (Long) getSession().createCriteria(User.class)
				.add(Restrictions.eq("name", name))
				.setProjection(Projections.rowCount())
				.uniqueResult();
		return (count > 0) ? true : false;
	}
	

	@Transactional
	public void setEnabled(User user) {
		user.setEnabled(true);
		getSession().saveOrUpdate(user);
	}

	@Transactional
	public Long getUserCount(UserRole role, String search) {
		Criteria createria = getSession().createCriteria(User.class);
		
		if(search != null && !search.isEmpty()){
			createria = search(createria, search); 
		}
		
		Long count = (Long) createria.add(Restrictions.eq("role", role)).setProjection(Projections.rowCount()).uniqueResult();
		return count;
	}

	@Transactional
	public List<User> getLimitedUserList(UserRole role, Integer offset, Integer limit, String search) {
		Criteria criteria = getSession().createCriteria(User.class);
		
		if(search != null && !search.isEmpty()){
			criteria = search(criteria, search); 
		}
		criteria.add(Restrictions.eq("role", role));
		
		@SuppressWarnings("unchecked")
		List<User> list = criteria.setFirstResult(offset).setMaxResults(limit).list();
		return list;
	}


	public Criteria search(Criteria criteria, String search) {
		search = '%' + search.trim() + '%';

		System.out.println("--------------------------");
		System.out.println("search = " + search);

		criteria = criteria.add(Restrictions.disjunction().add(Restrictions.like("name", search)).add(Restrictions.like("email", search)));
		return criteria;
	}
	
	@Transactional
	public User getUserByName(String name) {
		User user = (User) getSession().createCriteria(User.class)
				.add(Restrictions.eq("name", name))
				.uniqueResult();
		return user;
	}

	@Transactional
	public User updateCurrentUser(User user, Integer id) {
		Session session = getSession();
		
		User persistUser = (User) session.load(User.class, id);
		persistUser.setEmail(user.getEmail());
		persistUser.setBirthday(user.getBirthday());
		persistUser.setName(user.getName());
		session.flush();
		return persistUser;
	}
	
		
}
