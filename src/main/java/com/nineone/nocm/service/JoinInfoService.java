package com.nineone.nocm.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.nineone.nocm.domain.Invite;
import com.nineone.nocm.domain.JoinInfo;
import com.nineone.nocm.domain.LastAccess;

public interface JoinInfoService {
    boolean leaveUser(Map<String, Object> info);
    boolean isExistUser(Invite invite);
    boolean AuthorityCheck(Invite invite);
    boolean insertJoinInfo(JoinInfo joinInfo);
    int updateLastAccessDate(JoinInfo joinInfo);
//    LastAccess changeLastAccess(LastAccess lastAccess);
}
