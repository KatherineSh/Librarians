package com.librarians.dao.user;

import com.librarians.model.entity.VerificationToken;

public interface VerificationTokenDao {

	public void saveToken(VerificationToken token);
	
	public VerificationToken findToken(String tokenValue);
}
