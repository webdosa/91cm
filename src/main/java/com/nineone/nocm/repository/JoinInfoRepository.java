package com.nineone.nocm.repository;

import com.nineone.nocm.domain.Invite;
import com.nineone.nocm.domain.JoinInfo;

import java.util.List;
import java.util.Map;

public interface JoinInfoRepository {
    boolean leaveUser(Map<String, Object> info);
    boolean isExistUser(Invite invite);
    List<JoinInfo> getAllList();
    boolean channelAuthorityCheck(Invite invite);
    int insertJoinInfo(JoinInfo joinInfo);

}
