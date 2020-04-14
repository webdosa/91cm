package com.nineone.nocm.service;

import com.nineone.nocm.domain.Task;

public interface TaskService {
	public boolean insertTask(Task task);
	public boolean deleteTask(int id);
	public boolean updateTaskContent(Task task);
	public boolean updateTaskPosition(Task task);
}
