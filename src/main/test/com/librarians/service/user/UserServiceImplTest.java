package com.librarians.service.user;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.librarians.dao.user.UserDao;
import com.librarians.model.entity.User;
import com.librarians.model.service.UserProfile;
import com.librarians.model.service.UserRole;
import com.librarians.service.userToken.TokenService;

public class UserServiceImplTest {
	
	@InjectMocks
	private UserServiceImpl underTest;
	
	@Mock
	private TokenService tokenService;
	@Mock
	private UserProfile userProfile;
	@Mock
	private UserDao userDao; 
	
	@Before
	public void setUp(){
		underTest = new UserServiceImpl();
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testCreateLibrarian_shouldSaveNewLibrarianAndReturnId(){
		/*User newUser = new User();
		when(userDao.createNewUser(newUser)).thenReturn(Matchers.anyInt());
		Integer userId = underTest.createLibrarian(newUser);
		assertEquals(newUser.getId(), userId);
		verify(userDao).createNewUser(newUser);*/
		
		User userMock = Mockito.mock(User.class);
		when(userDao.createNewUser(userMock)).thenReturn(Matchers.anyInt());
		
		Integer userId = underTest.createLibrarian(userMock);
		
		verify(userMock).setRole(Matchers.eq(UserRole.LIBRARIAN));
		verify(userMock).setEnabled(Matchers.eq(true));
		assertNotNull(userId);
	}
	
	
}
