package com.nineone.nocm.service;

import com.nineone.nocm.domain.JoinInfo;

public interface JoinInfoService {
    boolean AuthorityCheck(JoinInfo joinInfo);
    boolean insertJoinInfo(JoinInfo joinInfo);
}
