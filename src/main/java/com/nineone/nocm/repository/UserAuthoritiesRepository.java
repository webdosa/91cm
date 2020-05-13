package com.nineone.nocm.repository;

import com.nineone.nocm.domain.Authorities;

import java.util.List;

public interface UserAuthoritiesRepository {
    List<String> getUserRoles(String email);
    int insertAuthority(Authorities authorities);
    int deleteAllAuthority(String email);
}
