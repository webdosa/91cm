package com.nineone.nocm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nineone.nocm.domain.User;
import com.nineone.nocm.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private PasswordEncoder passwordEncoder;	

	@Override
	public boolean insertUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return (userRepository.insertUser(user) > 0) ? true : false;
		
	}

	@Override
	public boolean idcheck(String userid) {
		return (userRepository.getUserid(userid) == null) ? true : false;
	}
	
}
