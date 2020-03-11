package com.nineone.nocm.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.nineone.nocm.domain.User;

public interface UserService {
	
	public boolean insertUser(User user);
	public boolean idcheck(String userid);
	UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException;
	
}
