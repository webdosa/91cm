package com.nineone.nocm.service;

import java.util.List;

import com.nineone.nocm.domain.TaskList;

public interface TaskListService {
	public boolean insertTaskList(TaskList taskList);
	public boolean deleteTaskList(int id);
	public boolean updateTaskListName(TaskList taskList);
	public List<TaskList> getTaskList(int channel_id);
}
