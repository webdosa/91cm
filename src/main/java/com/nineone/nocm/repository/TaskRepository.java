package com.nineone.nocm.repository;

import com.nineone.nocm.domain.Task;

public interface TaskRepository {

	public int insertTask(Task task);
	public int deleteTask(int id);
	public int updateTaskContent(Task task);
	public int updateTaskPosition(Task task);
}
