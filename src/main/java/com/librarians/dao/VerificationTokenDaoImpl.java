package com.librarians.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.librarians.model.VerificationToken;

@Component("tokenDao")
public class VerificationTokenDaoImpl extends AbstractDao implements VerificationTokenDao {

	@Transactional
	public void saveToken(VerificationToken token) {
		getSession().save(token);
	}

	@Transactional
	public VerificationToken findToken(String tokenValue) {

		@SuppressWarnings("unchecked")
		List<VerificationToken> tokenList = getSession().createCriteria(VerificationToken.class)
				 .add(Restrictions.eq("token", tokenValue)).list();
		
		VerificationToken result = null;		
		if(tokenList.size() > 0){
			result = tokenList.get(0);
		}
		return result;
	}
	
}
