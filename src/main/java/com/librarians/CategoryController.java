package com.librarians;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.librarians.model.UserRole;
import com.librarians.model.entity.Category;
import com.librarians.service.BookService;

@Controller
public class CategoryController {
	
	static Logger log = Logger.getLogger(CategoryController.class.getName());

	@Autowired
	private BookService bookService;
	
	
	@RequestMapping(path="/addCategory", method=RequestMethod.GET, produces="application/json" )
	public @ResponseBody Map<String, Object> addCategory(Authentication authentication, @RequestParam String categoryName){
		
		GrantedAuthority auth = new SimpleGrantedAuthority(UserRole.LIBRARIAN.toString());
		boolean isLibrarian = authentication.getAuthorities().contains(auth);

		Map<String, Object> result = new HashMap<String, Object>();
		if (isLibrarian && categoryName != null) {	
			String name = categoryName.trim();
			if(!name.isEmpty()) {
				
				boolean isExisted = bookService.isCategoryExisted(name);
				if(!isExisted){
					Category category = new Category();
					category.setCategoryName(name);
					boolean isCategoryAdded = bookService.addCategory(category);			
					result.put("isCategoryAdded", isCategoryAdded);
					
					List<Category> categories = bookService.getAllCategories();
					result.put("updatedCategories", categories);
				} else {
					result.put("isCategoryAdded", false);
					result.put("isCategoryExisted", true);
				}
			} else {
				result.put("isCategoryAdded", false);
			}
		}
		return result;
	}
	
}
