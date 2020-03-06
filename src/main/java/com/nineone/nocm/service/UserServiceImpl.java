package com.nineone.nocm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	public boolean idcheck(String userid) {
		return (userRepository.getUserid(userid) == null) ? true : false;
	}

	@Override
	public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
		Map<String,String> userInfo = (Map<String, String>) userRepository.getUserfindByUserId(userid);
		User user = User.builder()
				.userid(userInfo.get("userid"))
				.name(userInfo.get("name"))
				.email(userInfo.get("email"))
				.phone(userInfo.get("phone"))
				.icon(userInfo.get("icon"))
				.build();
		if (user == null){
			throw new UsernameNotFoundException("can not find user");
		}
		return user;
	}
}
