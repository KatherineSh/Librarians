package com.librarians.service.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;

import com.librarians.dao.user.UserDao;
import com.librarians.model.entity.User;
import com.librarians.model.entity.VerificationToken;
import com.librarians.model.service.UserProfile;
import com.librarians.model.service.UserRole;
import com.librarians.service.userToken.TokenService;

import org.junit.experimental.runners.Enclosed;

@RunWith(Enclosed.class)
public class UserServiceImplTest {
	
	public static class NotParamsUserServiceImplTest   {
		
		@InjectMocks
		protected UserServiceImpl underTest;
		
		@Mock
		protected TokenService tokenService;
		@Mock
		protected UserProfile userProfile;
		@Mock
		protected UserDao userDao; 
		
		@Before
		public void setUp(){
			underTest = new UserServiceImpl();
			MockitoAnnotations.initMocks(this);
		}
		
		@Test
		public void createLibrarian_shouldSaveNewLibrarianAndReturnId(){
			/*User newUser = new User();
			when(userDao.createNewUser(newUser)).thenReturn(Matchers.anyInt());
			Integer userId = underTest.createLibrarian(newUser);
			assertEquals(newUser.getId(), userId);
			verify(userDao).createNewUser(newUser);*/
			
			User librarianMock = Mockito.mock(User.class);
			when(userDao.createNewUser(librarianMock)).thenReturn(Matchers.anyInt());
			
			Integer userId = underTest.createLibrarian(librarianMock);
			
			verify(librarianMock).setRole(Matchers.eq(UserRole.LIBRARIAN));
			verify(librarianMock).setEnabled(Matchers.eq(true));
			assertNotNull(userId);
		}
		
		@Test
		public void createUser_shouldSaveNewUserAndReturnId(){
			User userMock = Mockito.mock(User.class);
			when(userDao.createNewUser(userMock)).thenReturn(Matchers.anyInt());
			
			Integer userId = underTest.createUser(userMock);
			assertNotNull(userId);
		}

		@Test
		public void setUserEnabled_shouldNotEnableUserIfTokenNotFound(){
			when(tokenService.findToken(Matchers.anyString())).thenReturn(null);
			
			boolean isEnabled = underTest.setUserEnabled(Matchers.anyString());
			
			verify(userDao, times(0)).setEnabled(new User());
			assertFalse(isEnabled);
		}
	}
	
	@RunWith(Parameterized.class)
	public static class VerifyUserEnablingWithParamsTestCase  {
		
		@InjectMocks
		protected UserServiceImpl underTest;
		@Mock
		protected TokenService tokenService;
		@Mock
		protected UserDao userDao; 
		
		@Before
		public void setUp(){
			underTest = new UserServiceImpl();
			MockitoAnnotations.initMocks(this);
		}
		
		@Parameter(0)
		public boolean isTokenNotExpiredParam;
		@Parameter(1)
		public Integer setUserEnableMethodCallTimesParam;
		
		@Parameters
	    public static Collection<Object[]> data() {
	        Object[][] data = new Object[][] { 
	        	{ true , 1 }, 
	        	{ false, 0 }
	        };
	        return Arrays.asList(data);
	    }
		
		@Test
		public void setUserEnabled_shoudVerifyEnablingUser(){
			VerificationToken token = new VerificationToken();
			User user = new User();
			token.setUser(user);
			
			boolean isExpired = isTokenNotExpiredParam;
			
			when(tokenService.findToken(Matchers.anyString())).thenReturn(token);
			when(tokenService.isTokenNotExpired(token)).thenReturn(isExpired);
			
			boolean isEnabled = underTest.setUserEnabled(Matchers.anyString());
			
			verify(userDao, times(setUserEnableMethodCallTimesParam)).setEnabled(user);
			assertEquals(isExpired, isEnabled);
		}
	}
	
	@RunWith(Parameterized.class)
	public static class VerifyUserDuplicationWithParamsTestCase  {
		
		@InjectMocks
		protected UserServiceImpl underTest;
		@Mock
		protected TokenService tokenService;
		@Mock
		protected UserDao userDao; 
		
		@Before
		public void setUp(){
			underTest = new UserServiceImpl();
			MockitoAnnotations.initMocks(this);
		}
		
		@Parameter(0)
		public boolean isUserEmailExistedParam;
		@Parameter(1)
		public boolean isUserNameExistedParam;
		@Parameter(2)
		public Integer emailRejectValueMathodCallTimesParam;
		@Parameter(3)
		public Integer nameRejectValueMathodCallTimesParam;
		
		@Parameters
	    public static Collection<Object[]> data() {
	        Object[][] data = new Object[][] { 
	        	{ true , true, 1, 1 }, 
	        	{ true , false, 1, 0 },
	        	{ false, true, 0, 1 },
	        	{ false, false, 0, 0 }
	        };
	        return Arrays.asList(data);
	    }
	    
	    @Test
		public void isUserUnique_shouldVeirfyEmailAndNameDuplication(){
			BindingResult result = Mockito.mock(BindingResult.class);
			User user = new User();
			user.setEmail("email");
			user.setName("name");
			
			when(userDao.isUserExistedByEmail(Matchers.anyString())).thenReturn(isUserEmailExistedParam);
			when(userDao.isUserExistedByName(Matchers.anyString())).thenReturn(isUserNameExistedParam);
			
			result = underTest.isUserUnique(user, result);
			
			verify(result, times(emailRejectValueMathodCallTimesParam)).rejectValue("email", "duplicate.email");
			verify(result, times(nameRejectValueMathodCallTimesParam)).rejectValue("name", "duplicate.name");
		}
	}
	
}
