package com.nineone.nocm.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.nineone.nocm.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
	
	boolean insertUser(User user);
	boolean emailCheck(String userEmail);
	UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException;

}
