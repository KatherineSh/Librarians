package com.librarians.service;

import org.springframework.validation.BindingResult;

import com.librarians.model.User;
import com.librarians.model.VerificationToken;

public interface UserService {
	
	public String findEmailByUserName(String name);
	
	public Integer createUser(User user);

	public boolean isExistedEmail(User user);
	
	public boolean isExistedName(User user);

	public boolean setUserEnabled(String tokenId);

	public BindingResult isUserUnique(User user, BindingResult result);
	
}
