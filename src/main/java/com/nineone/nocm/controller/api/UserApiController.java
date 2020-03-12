package com.nineone.nocm.controller.api;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nineone.nocm.annotation.Socialuser;
import com.nineone.nocm.domain.User;
import com.nineone.nocm.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserApiController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value="/login")
    public boolean userInit(@Socialuser User user){
		if(user!= null) {
			return user.getPhone() != null? true : false;
		}else {
			return false;
		}
    }
	
	@RequestMapping(value="/getsession")
	public User test(@Socialuser User user) {
		return user;
	}

    @RequestMapping(value = "/info")
    public String userInfo(@Socialuser User user){
	    return user.getEmail();
    }

    @RequestMapping(value="/signup",method=RequestMethod.POST)
    public boolean signup(@RequestBody User user) {
    	userService.insertUser(user);
    	return true;
    }
    
//    @RequestMapping("/idcheck")
//    public boolean idcheck(@RequestParam String userid) {
//    	return userService.emailCheck(userid);
//    }

    @GetMapping
    public List<Integer> getchannel() {
    	List<Integer> arr = new ArrayList<>();
    	arr.add(0);
    	arr.add(2);
    	arr.add(3);

    	return arr;
    }
    
}
