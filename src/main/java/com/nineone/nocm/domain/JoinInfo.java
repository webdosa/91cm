package com.nineone.nocm.domain;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JoinInfo {
	private int id;
	private int channel_id;
	private String register_date;
	private String member_email;
	private Date last_access_date;
	
	 @Builder
	 public JoinInfo(int id, int channel_id, String register_date, String member_email,Date last_access_date){
	        this.id = id;
	        this.channel_id= channel_id;
	        this.register_date = register_date;
	        this.member_email = member_email;
	        this.last_access_date = last_access_date;
	    }
	
}
