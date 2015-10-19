package com.librarians.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.librarians.dao.UserDao;
import com.librarians.model.User;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public String findEmailByUserName(String name) {
		String email = userDao.getEmailByUserName(name);
		return email;
	}

	@Override
	public Integer addUser(User user) {
		return userDao.createNewLibrarian(user);
	}

	@Override
	public boolean exist(User user) {
		return userDao.isUserExistedBy(user.getEmail());
	}

}
