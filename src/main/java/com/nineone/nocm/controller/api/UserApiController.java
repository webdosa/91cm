package com.nineone.nocm.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nineone.nocm.domain.User;
import com.nineone.nocm.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;

    @RequestMapping("/api")
    public User test(){
        return User.builder()
                .nickName("test")
                .email("test")
                .build();
    }
    
    
    @RequestMapping(value="/signup",method=RequestMethod.POST)
    public boolean signup(@RequestBody User user) {
  	
    	userService.insertUser(user);
    	
    	return true;
    }
}
