package com.librarians.model.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.librarians.dao.user.UserDao;
import com.librarians.model.entity.User;

@Component("userProfile")
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class UserProfileImpl implements UserProfile {

	private User user;

	@Autowired(required=true)
	private UserDao userDao;  
	
	public UserProfileImpl() {
	}
	
	@PostConstruct
	public void init() {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		this.user = userDao.getUserByName(userName);
		
		System.out.println("Create UserProfileImpl instance with name = " + userName);
	}
	
	public User getUser() {
		return user;
	}

	@Override
	public void updateUser(User user) {
		this.user = user;
		
	}	
}
