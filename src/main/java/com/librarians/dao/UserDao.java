package com.librarians.dao;

import com.librarians.model.User;

public interface UserDao {

	public String getEmailByUserName(String name);
	
	public Integer createNewLibrarian(User user);
}
