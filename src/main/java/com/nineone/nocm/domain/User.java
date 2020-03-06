package com.nineone.nocm.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class User {

    private String id;
    private String name;
    private String phone;
    private String email;
    private String icon;


    @Builder
    public User(String id,String name, String phone, String icon, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.icon = icon;
        this.email = email;
    }

    public User update(String name, String icon) {
        this.name = name;
        this.icon = icon;
        return this;
    }

}
