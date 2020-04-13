package com.nineone.nocm.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nineone.nocm.domain.TaskList;
import com.nineone.nocm.service.TaskListService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/tasklist")
public class TaskListController {
	
	@Autowired
	private TaskListService taskListService; 
	
	@RequestMapping(value="/insert", method = RequestMethod.POST)
	public boolean insertTaskList(@RequestBody TaskList taskList) {
		return taskListService.insertTaskList(taskList);
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public boolean deleteTaskList(@PathVariable int id) {
		return taskListService.deleteTaskList(id);
	}
	
	@RequestMapping(value="/update/name",method=RequestMethod.POST)
	public boolean updateTaskListName (@RequestBody TaskList taskList) {
		return taskListService.updateTaskListName(taskList);
	}
	
	@RequestMapping(value="/get/{channel_id}",method=RequestMethod.GET)
	public List<TaskList> getTaskList(@PathVariable int channel_id) {
		return taskListService.getTaskList(channel_id);
	}

}
