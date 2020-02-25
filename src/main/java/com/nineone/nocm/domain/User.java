package com.nineone.nocm.domain;

import com.nineone.nocm.domain.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class User{

    private Long idx;

    private String nickName;

    private String email;

    private String picture;

    private Role role;

    @Builder
    public User(String nickName, String email, String picture, Role role) {
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


}
