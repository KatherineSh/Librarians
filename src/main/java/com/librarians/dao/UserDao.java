package com.librarians.dao;

import com.librarians.model.User;

public interface UserDao {

	public String getEmailByUserName(String name);
	
	public Integer createNewUser(User user);

	public boolean isUserExistedByEmail(String email);
	
	public boolean isUserExistedByName(String name);

	public void setEnabled(User user);
}
