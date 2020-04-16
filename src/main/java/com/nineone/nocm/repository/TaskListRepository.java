package com.nineone.nocm.repository;

import java.util.List;
import java.util.Map;

import com.nineone.nocm.domain.TaskList;

public interface TaskListRepository {
	public int insertTaskList(TaskList taskList);
	public int deleteTaskList(int id);
	public int updateTaskPositionByDelete(int position);
	public int updateTaskListName(TaskList taskList);
	public List<TaskList> getTaskList(int channel_id);
//	public int initTaskListPosition(Map<String,Object> map); 
	public int moveTaskListPosition(Map<String,Object> map);
	public int updateTaskListPosition(Map<String,Object> map);
}
