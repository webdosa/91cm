package com.nineone.nocm.domain;

import java.time.LocalDateTime;


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
	
	 @Builder
	 public JoinInfo(int id, int channel_id, String register_date, String member_email){
	        this.id = id;
	        this.channel_id= channel_id;
	        this.register_date = register_date;
	        this.member_email = member_email;;
	    }
	
}
