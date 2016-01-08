package com.librarians.dao;

import java.util.List;

import com.librarians.model.User;
import com.librarians.model.UserRole;

public interface UserDao {

	public String getEmailByUserName(String name);
	
	public Integer createNewUser(User user);

	public boolean isUserExistedByEmail(String email);
	
	public boolean isUserExistedByName(String name);

	public void setEnabled(User user);

	public Long getUserCount(UserRole role, String search);

	public List<User> getLimitedUserList(UserRole role, Integer offset, Integer limit, String search);

}
