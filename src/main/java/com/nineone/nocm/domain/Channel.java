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


    @Builder
    public Channel(int id, String name){
        this.id = id;
        this.name = name;
    }
}
