package com.librarians.model;

import com.librarians.model.entity.User;

public interface UserProfile {
	
	public User getUser();
	
	public void updateUser(User user);
}
