package com.librarians.service;

import com.librarians.model.User;

public interface UserService {
	
	public String findEmailByUserName(String name);
	
	public Integer addUser(User user);

	public boolean exist(User user);

}
