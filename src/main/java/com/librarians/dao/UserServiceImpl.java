package com.librarians.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public String getUserEmailByLogin(String login) {
		
		String email = userDao.findEmailByUserLogin(login);
		return email;
	}

}
