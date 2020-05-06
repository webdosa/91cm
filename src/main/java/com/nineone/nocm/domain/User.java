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
public class User implements UserDetails {
    private String name;
    private String phone;
    private String email;
    private String picture;
    private String password;

    @Builder
    public User(String name, String phone, String picture, String email, String password) {
        this.name = name;
        this.phone = phone;
        this.picture = picture; 
        this.email = email;
        this.password = password;
        
    }

    public User update(String name, String icon) {
        this.name = name;
        this.picture = icon;
        return this;
    }

    //Form 로그인 설정
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
