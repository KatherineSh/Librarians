package com.librarians.dao;

import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.librarians.model.User;

@Repository
public class UserDaoImpl extends AbstractDao implements UserDao {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public String getEmailByUserName(String name) {
		Query query =  getSession().createQuery("select user.email from User as user where user.name = ?");
		query.setString(0, name);
		String email = (String) query.uniqueResult();
		return email;
	}
	
	@Override
	public Integer createNewLibrarian(User user) {
		user.setPass(passwordEncoder.encode(user.getPass()));
		
		Integer savedUserId = (Integer) getSession().save(user);
		return savedUserId;
	}

	@Override
	public boolean isUserExistedBy(String email) {
		Long count = (Long) getSession().createCriteria(User.class)
				.add(Restrictions.eq("email", email))
				.setProjection(Projections.rowCount())
				.uniqueResult();
		return (count > 0) ? true : false;
	}
}
