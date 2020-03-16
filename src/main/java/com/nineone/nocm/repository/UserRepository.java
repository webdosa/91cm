package com.nineone.nocm.repository;

import com.nineone.nocm.domain.User;

import java.util.List;

public interface UserRepository {
	
	int insertUser(User user);
	User getUserfindByEmail(String email);
    List<User> getAllUserList();
}
