package com.nineone.nocm.repository;

import java.util.Map;

import com.nineone.nocm.domain.Task;

public interface TaskRepository {

	public int insertTask(Task task);
	public int deleteTask(int id);
	public int updateTaskContent(Task task);
//	public int updateTaskPosition(Task task);
	public int updateTaskPositionByInsert(Task task);
	public int updateTaskPositionByDelete(Task task);
	public int moveTaskPosition(Map<String,Object> map);
	public int updateTaskPosition(Map<String,Object> map);
	public int moveTaskPositionByDelete(Map<String,Object> map);
	public int moveTaskPositionByinsert(Map<String,Object> map);
	
}
