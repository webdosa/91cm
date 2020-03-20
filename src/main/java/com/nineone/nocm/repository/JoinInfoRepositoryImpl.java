package com.nineone.nocm.repository;

import com.nineone.nocm.domain.Invite;
import com.nineone.nocm.domain.JoinInfo;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class JoinInfoRepositoryImpl implements JoinInfoRepository {

    @Autowired
    private SqlSessionTemplate sqlSession;

    private String namespace="com.nineone.nocm.mapper.joininfo";

    @Override
    public boolean leaveUser(Map<String, Object> info) {
        for (String key : info.keySet()){
            log.info(info.get(key)+"");
        }
        return (sqlSession.delete(namespace + ".leaveUser", info) > 0);
    }

    @Override
    public boolean isExistUser(Invite invite) {
        return sqlSession.selectOne(namespace + ".isExistUser",invite);
    }

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
