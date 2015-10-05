package com.librarians.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.librarians.model.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public String findEmailByUserLogin(String login) {	
		
		Query query =  sessionFactory.getCurrentSession().createQuery("select user.email from User as user where user.login = ?");
		query.setString(0, login);
		String email = (String) query.uniqueResult();

		return email;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
