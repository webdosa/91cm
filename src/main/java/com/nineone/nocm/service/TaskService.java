package com.nineone.nocm.service;

import java.util.Map;

import com.nineone.nocm.domain.Task;

public interface TaskService {
	public boolean insertTask(Task task);
	public boolean deleteTask(Task task);
	public boolean updateTaskContent(Task task);
//	public boolean updateTaskPosition(Task task);
	public boolean updateTaskPosition(Map<String,Object> map);
}
