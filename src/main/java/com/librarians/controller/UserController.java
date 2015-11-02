package com.librarians.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.librarians.model.User;
import com.librarians.model.UserRole;
import com.librarians.service.UserService;


@Controller
public class UserController {
	
	@Autowired
	private UserService userservice; 
	
	static Logger log = Logger.getLogger(UserService.class.getName());
	
	@RequestMapping(path="/userPage", method=RequestMethod.GET, produces="application/json")
	public @ResponseBody Map<String,Object> showUserListInTable(
			@RequestParam Integer offset,
			@RequestParam Integer limit){
		
		Long userCount = userservice.getUserCount(UserRole.USER);
		List<User> userList = userservice.listUser(UserRole.USER, offset, limit); 
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", userCount);
		result.put("rows", userList);
		
		return result;
	}

}
