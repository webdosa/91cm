package com.nineone.nocm.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notification {

	int id;
	int channel_id;
	String sender;
	String recipient;
	Date send_date;
	// invite_state
	
}
