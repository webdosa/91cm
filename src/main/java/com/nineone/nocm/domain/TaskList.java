package com.nineone.nocm.domain;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskList {
	private int id;
	private String name;
	private int channel_id;
	private Date register_date;
	private Date edit_date;
	//private int position;
	private List<Task> tasks;

}
