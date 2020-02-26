package com.nineone.nocm.domain;

import com.nineone.nocm.domain.enums.Role;

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

    private Long idx;
    
    private String userid;
    private String password;
    private String name;
    private String phone;
    private String email;
    private String icon;
    
    private String picture;
    
    private String nickName;


    private Role role;

    @Builder
    public User(String userid, String password, String name, String phone, String icon,
                String nickName, String email, String picture, Role role) {
        this.userid = userid;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.icon = icon;
        this.nickName = nickName;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User update(String nickName, String picture) {
        this.nickName = nickName;
        this.picture = picture;
        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return userid;
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
