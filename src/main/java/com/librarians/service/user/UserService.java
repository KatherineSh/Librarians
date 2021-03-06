package com.librarians.service.user;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.librarians.model.entity.User;
import com.librarians.model.service.UserProfile;
import com.librarians.model.service.UserRole;

public interface UserService {
	
	public String getEmailByName(String name);
	
	public Integer createUser(User user);

	public Integer createLibrarian(User librarian);

	public boolean setUserEnabled(String tokenId);
	
	public boolean isExistedEmail(User user);
	
	public boolean isExistedName(User user);

	public BindingResult isUserUnique(User user, BindingResult result);

	public Long getUserCount(UserRole role, String search);

	public List<User> search(UserRole role, Integer offset, Integer limit, String search);

	public UserProfile getUserProfile();

	public void updateCurrentUser(User user);
}
