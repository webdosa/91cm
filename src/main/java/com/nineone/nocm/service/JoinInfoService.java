package com.nineone.nocm.service;

import com.nineone.nocm.domain.Invite;
import com.nineone.nocm.domain.JoinInfo;

public interface JoinInfoService {
    boolean isExistUser(Invite invite);
    boolean AuthorityCheck(Invite invite);
    boolean insertJoinInfo(JoinInfo joinInfo);
}
