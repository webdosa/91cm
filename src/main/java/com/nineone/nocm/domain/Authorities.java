package com.nineone.nocm.domain;

import com.nineone.nocm.domain.enums.Role;
import lombok.Builder;

public class Authorities {

    private String member_email;
    private String roles_authority;

    @Builder
    public Authorities(String member_email, String roles_authority){
        this.member_email = member_email;
        this.roles_authority = roles_authority;
    }
}
