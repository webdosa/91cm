package com.nineone.nocm.service;

import com.nineone.nocm.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
	
	public boolean insertUser(User user);
	public boolean emailCheck(String userid);
	
}
