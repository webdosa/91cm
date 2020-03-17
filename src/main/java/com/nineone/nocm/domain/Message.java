package com.nineone.nocm.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
	private int id;
	private int channel_id;
	private String content;
	private String sender;
	private String send_date;

	@Builder
	public Message(int id,int channel_id, String content, String sender,String send_date){
		this.id = id;
		this.channel_id = channel_id;
		this.content = content;
		this.sender = sender;
		this.send_date = send_date;
	}
}
