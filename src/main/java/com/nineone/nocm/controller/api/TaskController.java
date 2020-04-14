package com.nineone.nocm.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nineone.nocm.domain.Task;
import com.nineone.nocm.service.TaskService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/task")
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	@RequestMapping(value="/insert",method = RequestMethod.POST)
	public boolean insertTask(@RequestBody Task task) {
		return taskService.insertTask(task);
	}
	
	@RequestMapping(value="/delete/{id}",method = RequestMethod.POST)
	public boolean deleteTask(@PathVariable int id) {
		return taskService.deleteTask(id);
	}
	
	@RequestMapping(value="/update/content",method = RequestMethod.POST)
	public boolean updateTaskContent(@RequestBody Task task) {
		return taskService.updateTaskContent(task);
	}
	
	@RequestMapping(value="/update/position",method = RequestMethod.POST)
	public boolean updateTaskPosition(@RequestBody Task task) {
		return taskService.updateTaskPosition(task);
	}

}
