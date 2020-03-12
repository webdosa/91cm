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
    private String member_email;

    @Builder
    public Channel(int id, String name, LocalDateTime register_date, String member_email){
        this.id = id;
        this.name = name;
        this.register_date = register_date;
        this.member_email = member_email;
    }
}
