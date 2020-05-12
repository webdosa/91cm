package com.nineone.nocm.controller.api;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	private SimpMessageSendingOperations messagingTemplate;
	@Autowired
	private UserService userService;

	@RequestMapping("/list")
    public List<User> userList(){
	    return userService.getAllUserList();
    }

	@RequestMapping(value="/login")
    public boolean userInit(@Socialuser User user){
		if(user!= null) {
			return user.getPhone() != null? true : false;
		}else {
			return false; 
		} 
    }
    @PostMapping("/update")
	public boolean userInfoUpdate(@RequestBody User user){
		return userService.userinfoUpdate(user);
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
    	if (userService.insertUser(user,oauth2user,httpsession)){
			messagingTemplate.convertAndSend("/sub/sync/info","userList");
    		return true;
		}else{
    		return false;
		}
    }
    @RequestMapping(value = "/channel/{channel_id}")
	public List<User> getChannelUserList(@PathVariable int channel_id){
		return userService.getCurrentChannelUserList(channel_id);
	}
    
    @RequestMapping("/test/t")
    public void testapi() {
    	log.info("ddddddddddddddddddd");
    }
  
    @RequestMapping("/test/t2")
    public void testapi2() {
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
    	DefaultOAuth2User userDetails = (DefaultOAuth2User)principal; 
    
    	log.info(userDetails.getAttributes().toString());
    	log.info(userDetails.getAuthorities().toString());
    

    }
}
