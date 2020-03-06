package com.nineone.nocm.controller.api;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nineone.nocm.annotation.Socialuser;
import com.nineone.nocm.domain.User;
import com.nineone.nocm.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("/api/user")
public class UserApiController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/test")
    public boolean test(){
	    return true;
    }

	@RequestMapping(value="/login")
    public boolean userInit(@Socialuser User user){
	    return user != null? true : false;
    }

    @RequestMapping(value = "/info")
    public String userInfo(@Socialuser User user){
	    return user.getUserid();
    }
    @RequestMapping(value="/signup",method=RequestMethod.POST)
    public boolean signup(@RequestBody User user) {
    	userService.insertUser(user);
    	return true;
    }
    
    @RequestMapping("/idcheck")
    public boolean idcheck(@RequestParam String userid) {
    	return userService.idcheck(userid);
    }
    
    @RequestMapping("/user")
    public List<Integer> getchannel() {
    	
    	List<Integer> arr = new ArrayList<>();
    	arr.add(1);
    	arr.add(2);
    	arr.add(3);
    	
    	return arr;
    }
    
    
    
}
