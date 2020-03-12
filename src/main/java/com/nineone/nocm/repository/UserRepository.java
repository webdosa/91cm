package com.nineone.nocm.repository;

import com.nineone.nocm.domain.User;

public interface UserRepository {
	int insertUser(User user);
	User getUserfindByEmail(String email);
}
