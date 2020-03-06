package com.nineone.nocm.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Channel {
    private int id;
    private String name;
    private String member_id;


    @Builder
    public Channel(int id, String name, String member_id){
        this.id = id;
        this.name = name;
        this.member_id = member_id;
    }
}
