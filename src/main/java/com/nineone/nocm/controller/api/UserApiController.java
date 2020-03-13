package com.nineone.nocm.controller.api;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
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
    public User userInfo(@Socialuser User user){
	    return user;
    }

    @RequestMapping(value="/signup",method=RequestMethod.POST)
    public boolean signup(@RequestBody User user ,Authentication authentication,HttpSession httpsession) {
    	DefaultOAuth2User oauth2user = (DefaultOAuth2User)authentication.getPrincipal();
    	return userService.insertUser(user,oauth2user,httpsession);
    }
    

}
