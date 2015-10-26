package com.librarians.dao;

import com.librarians.model.VerificationToken;

public interface VerificationTokenDao {

	public void saveToken(VerificationToken token);
	
	public VerificationToken findToken(String tokenValue);
}
