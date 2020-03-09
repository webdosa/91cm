package com.nineone.nocm;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nineone.nocm.domain.User;
import com.nineone.nocm.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserTest {

	private static final Logger logger = LoggerFactory.getLogger(UserTest.class);
	
	@Autowired
	private UserService userService;
	
//	@Test
//	public void idcheck() {
//		logger.info("1 : "+userService.idcheck("asd"));
//		logger.info("2 : "+userService.idcheck("asds"));
//	}
//	
//	//@Test
//	public void inserttest() {
//		User user = new User();
//		user.setEmail("asd");
//		user.setIcon("asd");
//		user.setUserid("asd");
//		user.setName("asd");
//		user.setPassword("asd");
//		user.setPhone("asd");
//		
//		userService.insertUser(user);
//	}
}
