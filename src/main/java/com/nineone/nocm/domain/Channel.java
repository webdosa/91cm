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
    private LocalDateTime dateTime;
    private User user;

    @Builder
    public Channel(int id, String name, LocalDateTime dateTime, User user){
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
        this.user = user;
    }
}
