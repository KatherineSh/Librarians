package com.librarians.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.librarians.dao.UserDao;
import com.librarians.dao.VerificationTokenDao;
import com.librarians.model.User;
import com.librarians.model.UserRole;
import com.librarians.model.VerificationToken;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public String findEmailByUserName(String name) {
		String email = userDao.getEmailByUserName(name);
		return email;
	}

	@Override
	public Integer createUser(User user) {
		return userDao.createNewUser(user);
	}
	
	@Override
	public Integer createLibrarian(User librarian) {
		librarian.setRole(UserRole.LIBRARIAN);
		librarian.setEnabled(true);
		return userDao.createNewUser(librarian);
	}

	@Override
	public boolean isExistedEmail(User user) {
		return userDao.isUserExistedByEmail(user.getEmail());
	}
	
	@Override
	public boolean isExistedName(User user) {
		return userDao.isUserExistedByName(user.getName());
	}

	@Override
	public boolean setUserEnabled(String tokenId) {
		
		VerificationToken token = tokenService.findToken(tokenId);
		
		if(token != null && tokenService.isTokenNotExpired(token)){
			User user = token.getUser();
			userDao.setEnabled(user);
			return true;
		}
		return false;
	}
	
	@Override
	public BindingResult isUserUnique(User user, BindingResult result){
		if(isExistedEmail(user)){
			result.rejectValue("email", "duplicate.email");
		}
		if(isExistedName(user)){
			result.rejectValue("name", "duplicate.name");
		}
		return result;	
	}
	
}
