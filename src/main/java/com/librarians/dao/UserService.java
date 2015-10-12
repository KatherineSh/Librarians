package com.librarians.dao;

import com.librarians.model.User;

public interface UserService {
	
	public String findEmailByUserName(String name);
	
	public Integer addLibrarian(User librarian);

}
