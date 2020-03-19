package com.nineone.nocm.domain;

import lombok.Builder;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Message {
	int id;
	int channel_id;
	String content;
	String sender;
	Date send_date;
	String str_send_date;
	User user;

	@Builder
	public Message(int id,int channel_id, String content, String sender, Date send_date,
				   User user, String str_send_date){
		this.id = id;
		this.channel_id = channel_id;
		this.content = content;
		this.sender = sender;
		this.send_date = send_date;
		this.str_send_date = str_send_date;
		this.user = user;
	}

}
