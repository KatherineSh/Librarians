package com.librarians.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.librarians.model.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public String getEmailByUserName(String name) {
		Query query =  openSession().createQuery("select user.email from User as user where user.name = ?");
		query.setString(0, name);
		String email = (String) query.uniqueResult();

		return email;
	}
	
	@Override
	public Integer createNewLibrarian(User user) {
		Integer savedUserId = (Integer) openSession().save(user);
		 return savedUserId;
	}
	
	private Session openSession(){
		return sessionFactory.getCurrentSession();
	}
}
