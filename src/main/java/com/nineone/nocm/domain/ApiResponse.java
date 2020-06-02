package com.nineone.nocm.domain;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiResponse {
    private String error;
    private String message;
    private List<String> list;

    @Builder
    public ApiResponse(String error, String message,List<String> list){
        this.error = error;
        this.message = message;
        this.list = list;
    }
}
