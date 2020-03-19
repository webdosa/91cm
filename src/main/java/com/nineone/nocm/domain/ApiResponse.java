package com.nineone.nocm.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiResponse {
    private String error;
    private String message;

    @Builder
    public ApiResponse(String error, String message){
        this.error = error;
        this.message = message;
    }
}
