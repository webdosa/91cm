package com.nineone.nocm.repository;

import com.nineone.nocm.domain.Invite;
import com.nineone.nocm.domain.JoinInfo;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JoinInfoRepositoryImpl implements JoinInfoRepository {

    @Autowired
    private SqlSessionTemplate sqlSession;

    private String namespace="com.nineone.nocm.mapper.joininfo";
    @Override
    public List<JoinInfo> getAllList() {
        return sqlSession.selectList(namespace + ".getAllList");
    }

    @Override
    public boolean channelAuthorityCheck(Invite invite) {
        return sqlSession.selectOne(namespace + ".channelAuthorityCheck", invite);
    }

    @Override
    public int insertJoinInfo(JoinInfo joinInfo) {
        return sqlSession.insert(namespace + ".insertJoinInfo",joinInfo);
    }


}
