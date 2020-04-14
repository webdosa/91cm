package com.nineone.nocm.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nineone.nocm.domain.TaskList;
import com.nineone.nocm.repository.TaskListRepository;
import com.nineone.nocm.util.DateUtil;

@Service
public class TaskListServiceImpl implements TaskListService {
	
	@Autowired
	private TaskListRepository taskListRepository;

	@Override
	public boolean insertTaskList(TaskList taskList) {
		return (taskListRepository.insertTaskList(taskList) > 0 )? true: false;
	}

	@Override
	public boolean deleteTaskList(int id) {
		return (taskListRepository.deleteTaskList(id) > 0 )? true: false;
	}

	@Override
	public boolean updateTaskListName(TaskList taskList) {
		Date edit_date = DateUtil.makeDate();
		taskList.setEdit_date(edit_date);
		return (taskListRepository.updateTaskListName(taskList) > 0 )? true: false;
	}

	@Override
	public List<TaskList> getTaskList(int channel_id) {
		return taskListRepository.getTaskList(channel_id);
	}

}
