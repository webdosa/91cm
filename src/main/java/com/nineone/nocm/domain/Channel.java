package com.nineone.nocm.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Channel {
    private int id;
    private String name;
    private LocalDateTime register_date;
    private String member_email;  //채널 생성자
    private String login_email; //현재 로그인 유저
    private int count;
    
    @Builder
    public Channel(int id, String name, LocalDateTime register_date, String member_email,int count){
        this.id = id;
        this.name = name;
        this.register_date = register_date;
        this.member_email = member_email;
        this.count = count;
    }
}
