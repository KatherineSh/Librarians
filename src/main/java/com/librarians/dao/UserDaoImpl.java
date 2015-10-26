package com.librarians.dao;

import org.hibernate.Query;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.librarians.model.User;

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
}
