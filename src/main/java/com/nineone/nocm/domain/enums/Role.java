package com.nineone.nocm.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    // 임시 클래스 변경 필요
    GUSET("ROLE_ANON", "제한된 사용자"),
    USER("ROLE_USER", "일반 사용자"),
    ADMIN("ROLE_ADMIN", "관리자");
    private final String key;
    private final String title;
}
