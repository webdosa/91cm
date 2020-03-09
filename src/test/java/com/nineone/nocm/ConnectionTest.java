package com.nineone.nocm;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ConnectionTest {
	private static final Logger logger = LoggerFactory.getLogger(ConnectionTest.class);
	
	
	@Autowired
	private SqlSessionTemplate session;
	
	@Test
	public void testSelect() {


		int a = session.selectOne("com.nineone.nocm.mapper.TestMapper.selecttest");

		logger.info("\n Session Factory : ");
		logger.info("\n Session Factory : " + a);
	}

}
