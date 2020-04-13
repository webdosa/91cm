package com.nineone.nocm.repository;

import java.util.List;

import com.nineone.nocm.domain.TaskList;

public interface TaskListRepository {
	public int insertTaskList(TaskList taskList);
	public int deleteTaskList(int id);
	public int updateTaskListName(TaskList taskList);
	public List<TaskList> getTaskList(int channel_id);
}
