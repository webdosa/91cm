package com.nineone.nocm.repository;

import com.nineone.nocm.domain.User;

public interface UserRepository {
	
	public int insertUser(User user);
	public String getUserid(String userid);
	
}
