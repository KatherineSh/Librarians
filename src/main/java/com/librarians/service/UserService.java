package com.librarians.service;

import org.springframework.validation.BindingResult;

import com.librarians.model.User;
import com.librarians.model.VerificationToken;

public interface UserService {
	
	public String findEmailByUserName(String name);
	
	public Integer createUser(User user);

	public Integer createLibrarian(User librarian);

	public boolean setUserEnabled(String tokenId);
	
	public boolean isExistedEmail(User user);
	
	public boolean isExistedName(User user);

	public BindingResult isUserUnique(User user, BindingResult result);

}
