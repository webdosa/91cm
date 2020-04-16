package com.nineone.nocm.service;

import java.util.List;
import java.util.Map;

import com.nineone.nocm.domain.TaskList;

public interface TaskListService {
	public boolean insertTaskList(TaskList taskList);
	public boolean deleteTaskList(TaskList taskList);
	public boolean updateTaskListName(TaskList taskList);
	public List<TaskList> getTaskList(int channel_id);
	public boolean updateTaskListPosition(Map<String,Object> map);
}
