package com.nineone.nocm.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ContentsFile {
    private int id;
    private String original_name;
    private String server_name;
    private String path;
    private String extension;
    private Date datetime;
    private int message_id;
    private String sender;

    @Builder
    public ContentsFile(int id, String original_name, String server_name, String path,
                        String extension, Date datetime, int message_id, String sender) {
        this.id = id;
        this.original_name = original_name;
        this.server_name = server_name;
        this.path = path;
        this.extension = extension;
        this.datetime = datetime;
        this.message_id = message_id;
        this.sender = sender;
    }
}
