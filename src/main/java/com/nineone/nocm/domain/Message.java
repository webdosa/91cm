package com.nineone.nocm.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
	int id;
	int channel_id;
	String content;
	String sender;
	String send_date;
}
