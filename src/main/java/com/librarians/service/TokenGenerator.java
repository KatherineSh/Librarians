package com.librarians.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.librarians.dao.VerificationTokenDao;
import com.librarians.model.entity.User;
import com.librarians.model.entity.VerificationToken;

@Component("tokenGenerator")
public class TokenGenerator {
	
	@Autowired
	private VerificationTokenDao tokenDao;
	
	public String generateAndSaveToken(User user) {
		String tokenValue = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken(user, tokenValue);
		
		tokenDao.saveToken(verificationToken);
		return tokenValue;
	}
}
