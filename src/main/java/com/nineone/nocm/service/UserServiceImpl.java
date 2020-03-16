package com.nineone.nocm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import com.nineone.nocm.domain.User;
import com.nineone.nocm.repository.UserRepository;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;


	@Override
	public boolean insertUser(User user) {
//		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return (userRepository.insertUser(user) > 0) ? true : false;
		
	}

	@Override
	public boolean emailCheck(String userEmail) {
		return (userRepository.getUserfindByEmail(userEmail) == null) ? true : false;
	}

	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		Map<String,String> userInfo = (Map<String, String>) userRepository.getUserfindByEmail(userEmail);
		User user = User.builder()
				.name(userInfo.get("name"))
				.email(userInfo.get("email"))
				.phone(userInfo.get("phone"))
				.picture(userInfo.get("picture"))
				.build();
		if (user == null){
			throw new UsernameNotFoundException("can not find user");
		}
		return (UserDetails) user;
	}

	@Override
	public List<User> getAllUserList() {
		return userRepository.getAllUserList();
	}

}
