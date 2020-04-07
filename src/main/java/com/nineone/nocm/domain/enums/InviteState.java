package com.nineone.nocm.domain.enums;

public enum InviteState {
    STAND_BY("대기"),
    ACCEPT("수락"),
    REFUSE("거절");

    private String value;

    InviteState(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
