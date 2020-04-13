package com.nineone.nocm.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nineone.nocm.domain.Task;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

	@Autowired
    private SqlSessionTemplate sqlSession;

	private String namespace = "com.nineone.nocm.mapper.task";
	
	@Override
	public int insertTask(Task task) {
		return sqlSession.insert(namespace + ".insertTask",task);
	}

	@Override
	public int deleteTask(int id) {
		return sqlSession.delete(namespace + ".deleteTask", id);
	}

	@Override
	public int updateTaskContent(Task task) {
		return sqlSession.update(namespace + ".updateTaskContent",task);
	}

	@Override
	public int updateTaskPosition(Task task) {
		return sqlSession.update(namespace + ".updateTaskPosition",task);
	}

}
