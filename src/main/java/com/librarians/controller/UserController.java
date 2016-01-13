package com.librarians.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.librarians.model.UserProfile;
import com.librarians.model.UserRole;
import com.librarians.model.entity.User;
import com.librarians.service.UserService;


@Controller
public class UserController {
	
	@Autowired
	private UserService userservice; 
	
	static Logger log = Logger.getLogger(UserService.class.getName());
	
	@RequestMapping(path="/userPage", method=RequestMethod.GET, produces="application/json")
	public @ResponseBody Map<String,Object> showUserListInTable(
			@RequestParam Integer offset,
			@RequestParam Integer limit,
			@RequestParam(required=false) String order,
			@RequestParam(required=false) String sort,
			@RequestParam(required=false) String search	)
	{
		
		Long userCount = userservice.getUserCount(UserRole.USER, search);
		List<User> userList = userservice.listUser(UserRole.USER, offset, limit, search); 
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", userCount);
		result.put("rows", userList);
		
		return result;
	}
	
	@RequestMapping(path="/profile", method=RequestMethod.GET)
	public String showUserProfile(Model map){
		
		UserProfile profile = userservice.getUserProfile();
		map.addAttribute("userProfile", profile);
		return "user/profile";
	}
	
	@RequestMapping(path="/editProfile", method=RequestMethod.GET)
	public String showEditProfileForm(Model map){

		UserProfile profile = userservice.getUserProfile();
		map.addAttribute("user", profile.getUser());
		return "user/editProfile";
	}
	
	@RequestMapping(path="/editUser", method=RequestMethod.POST)
	public String editUserDetails(
			RedirectAttributes redirectAttrs,
			@ModelAttribute("user") User user){

		userservice.updateCurrentUser(user);
		System.out.println("save edited user");
		
		redirectAttrs.addFlashAttribute("changesSaved",true);
		return "redirect:/profile";
	}

}
