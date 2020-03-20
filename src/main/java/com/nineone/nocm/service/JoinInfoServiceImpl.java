package com.nineone.nocm.service;

import com.nineone.nocm.domain.Invite;
import com.nineone.nocm.domain.JoinInfo;
import com.nineone.nocm.repository.JoinInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JoinInfoServiceImpl implements JoinInfoService {

    @Autowired
    private JoinInfoRepository joinInfoRepository;

    @Override
    public boolean leaveUser(Map<String, Object> info) {
        return joinInfoRepository.leaveUser(info);
    }

    @Override
    public boolean isExistUser(Invite invite) {
        return joinInfoRepository.isExistUser(invite);
    }

    @Override
    public boolean AuthorityCheck(Invite invite) {
       return joinInfoRepository.channelAuthorityCheck(invite);
    }

    @Override
    public boolean insertJoinInfo(JoinInfo joinInfo) {
        return (joinInfoRepository.insertJoinInfo(joinInfo) > 0);
    }
}
