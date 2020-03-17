package com.nineone.nocm.service;

import com.nineone.nocm.domain.JoinInfo;
import com.nineone.nocm.repository.JoinInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JoinInfoServiceImpl implements JoinInfoService {

    @Autowired
    private JoinInfoRepository joinInfoRepository;

    @Override
    public boolean AuthorityCheck(JoinInfo joinInfo) {
        if (joinInfoRepository.channelAuthorityCheck(joinInfo) == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean insertJoinInfo(JoinInfo joinInfo) {
        return (joinInfoRepository.insertJoinInfo(joinInfo) > 0);
    }
}
