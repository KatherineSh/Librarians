package com.librarians.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.librarians.model.User;
import com.librarians.model.UserRole;

@Component("userDao")
public class UserDaoImpl extends AbstractDao implements UserDao {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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
	public Long getUserCount(UserRole role) {
		Long count = (Long) getSession().createCriteria(User.class)
				.add(Restrictions.eq("role", role))
				.setProjection(Projections.rowCount()).uniqueResult();
		return count;
	}

	@Transactional
	public List<User> getLimitedUserList(UserRole role, Integer offset, Integer limit) {
		Criteria criteria = getSession().createCriteria(User.class)
				.add(Restrictions.eq("role", role));
		
		@SuppressWarnings("unchecked")
		List<User> list = criteria.setFirstResult(offset).setMaxResults(limit).list();
		return list;
	}
}
