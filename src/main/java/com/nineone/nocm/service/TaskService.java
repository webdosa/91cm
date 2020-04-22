package com.nineone.nocm.service;

import java.util.Map;

import com.nineone.nocm.domain.Task;

public interface TaskService {
	boolean insertTask(Task task);
	boolean deleteTask(Task task);
	boolean updateTaskContent(Task task);
	boolean updateTaskPosition(Map<String,Object> map);
}
