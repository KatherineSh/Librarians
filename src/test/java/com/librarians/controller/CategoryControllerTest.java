package com.librarians.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.librarians.model.entity.Category;
import com.librarians.model.service.UserRole;
import com.librarians.service.book.BookService;

@RunWith(Parameterized.class)
public class CategoryControllerTest {
	
	@Mock
	private BookService bookServiceMock;
	@Mock
	private Collection<? extends GrantedAuthority> grantedAuth;
	@Mock
	private Authentication authenticationMock;

	@InjectMocks
	private CategoryController underTest;
	
	private MockMvc mockMvc;
	
	@Parameter(0)
	public String roleParam;
	@Parameter(1)
	public boolean isExistedParam;
	@Parameter(2)
	public boolean isCategoryAddedParam;
	@Parameter(3)
	public String categoryNameParam;
	
	
	@Parameters 
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][]{
			
			{UserRole.USER.toString(), false, false, "test_Category"},
			//{UserRole.LIBRARIAN.toString(), false, true, new String()},
			{UserRole.LIBRARIAN.toString(), false, false, " "},
			
			{UserRole.LIBRARIAN.toString(), false, true, "Art"},
			{UserRole.LIBRARIAN.toString(), true, false, "Art"},
			
			{UserRole.LIBRARIAN.toString(), false, true, "test_Category"}
		});
	};
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(underTest).build();
	}
	
	@Test
	public void addCategory_shouldAddNewCategory() throws Exception {
		GrantedAuthority auth = new SimpleGrantedAuthority(roleParam);
		List grantedAuthorities = new ArrayList();
		grantedAuthorities.add(auth);
		when(authenticationMock.getAuthorities()).thenReturn(grantedAuthorities);  //isLibrarian
		when(bookServiceMock.isCategoryExisted(Matchers.anyString())).thenReturn(isExistedParam); //isExisted
		when(bookServiceMock.addCategory(Matchers.any(Category.class))).thenReturn(isCategoryAddedParam); //isCategoryAdded
		
		List<Category> categories = new ArrayList<Category>();
		categories.add(new Category("Art"));
		categories.add(new Category("History"));
		when(bookServiceMock.getAllCategories()).thenReturn(categories);//List categories
		
		String categoryName = categoryNameParam;//"test_Category";
		
		ResultActions actions = this.mockMvc.perform(get("/addCategory")
				.param("categoryName", categoryName)
				.principal(authenticationMock))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.isCategoryAdded").value(isCategoryAddedParam));
		
		if(roleParam.equals("LIBRARIAN") && categoryNameParam != null && !categoryNameParam.trim().isEmpty()){
			if(!isExistedParam) {
				actions.andExpect(jsonPath("$.updatedCategories", org.hamcrest.Matchers.hasSize(categories.size())));
			} else {
				actions.andExpect(jsonPath("$.isCategoryExisted").value(isExistedParam));
			}
		}
	}

}
