package com.librarians.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.librarians.dao.VerificationTokenDao;
import com.librarians.model.entity.VerificationToken;

@Service("tokenService")
public class TokenService {

	@Autowired
	private VerificationTokenDao tokenDao;
	
	public boolean isTokenNotExpired(VerificationToken token){
		Date expiryDate = token.getExpiryDate();
		Date currentDate = new Date();
		
		if(currentDate.after(expiryDate)){
			return false;
		}
		return true;
	}
	
	public VerificationToken findToken(String tokenId) {
		return tokenDao.findToken(tokenId);	
	}
}
