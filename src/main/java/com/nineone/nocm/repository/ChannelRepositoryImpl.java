package com.nineone.nocm.repository;

import com.nineone.nocm.domain.Channel;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ChannelRepositoryImpl implements ChannelRepository{
    @Autowired
    private SqlSessionTemplate sqlSession;

    private String namespace = "com.nineone.nocm.mapper.channel";
    @Override
    public int insertChannel(Channel channel) {
        return sqlSession.insert(namespace + ".insertChannel",channel);
    }

    @Override
    public int deleteChannel(int channelId) {
        return sqlSession.delete(namespace+".deleteChannel", channelId);
    }

    @Override
    public int updateChannel(Channel channel) {
        return sqlSession.update(namespace + ".updateChannel",channel);
    }
}
