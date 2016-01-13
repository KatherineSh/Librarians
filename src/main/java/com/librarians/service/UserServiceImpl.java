package com.librarians.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.librarians.dao.UserDao;
import com.librarians.model.UserProfile;
import com.librarians.model.UserRole;
import com.librarians.model.entity.User;
import com.librarians.model.entity.VerificationToken;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UserProfile userProfile;
	
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

	@Override
	public Long getUserCount(UserRole role,String search) {
		return userDao.getUserCount(role, search);
	}

	@Override
	public List<User> listUser(UserRole role, Integer offset, Integer limit, String search) {
		return userDao.getLimitedUserList(role, offset, limit, search);
	}

	@Override
	public UserProfile getUserProfile() {
		return this.userProfile;
	}
	
	@Override
	public void updateCurrentUser(User user) {
		User currentUser = userProfile.getUser();
		User cangedCurrentUser = userDao.updateCurrentUser(user, currentUser.getId());
		userProfile.updateUser(cangedCurrentUser);
	}
}
