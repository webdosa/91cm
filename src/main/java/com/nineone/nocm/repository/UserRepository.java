package com.nineone.nocm.repository;

import java.util.Map;

import com.nineone.nocm.domain.User;

public interface UserRepository {
	public int insertSNSInfo(Map<String,Object> map);
	int insertUser(User user);
	User getUserfindById(String email);
}
