package com.nineone.nocm.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nineone.nocm.domain.Message;

@Repository
public class MessageRepositoryImpl implements MessageRepository {
	
	@Autowired
    private SqlSessionTemplate sqlSession;

	private String namespace = "com.nineone.nocm.mapper.message";
	
	@Override
	public int insertMessage(Message msg) {
		return sqlSession.insert(namespace + ".insertMessage",msg);
	}

}
