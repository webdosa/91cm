package com.nineone.nocm.repository;

import com.nineone.nocm.domain.Invite;
import com.nineone.nocm.domain.JoinInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JoinInfoRepository {
    List<JoinInfo> getAllList();
    JoinInfo channelAuthorityCheck(JoinInfo joinInfo);
    int insertJoinInfo(JoinInfo joinInfo);

}
