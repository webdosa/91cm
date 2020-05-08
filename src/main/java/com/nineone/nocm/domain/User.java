package com.nineone.nocm.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@NoArgsConstructor
@Setter
public class User {
    private String name;
    private String phone;
    private String email;
    private String picture;


    @Builder
    public User(String name, String phone, String picture, String email, String password) {
        this.name = name;
        this.phone = phone;
        this.picture = picture; 
        this.email = email;
        
    }

    public User update(String name, String icon) {
        this.name = name;
        this.picture = icon;
        return this;
    }

}
