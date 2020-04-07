package com.nineone.nocm.repository;

import com.nineone.nocm.domain.Invite;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InviteRepositoryImpl implements InviteRepository{

    @Autowired
    private SqlSessionTemplate sqlSession;

    private String namespace = "com.nineone.nocm.mapper.invite";

    @Override
    public int saveInvite(Invite invite) {
        return sqlSession.insert(namespace+".saveInvite",invite);
    }

    @Override
    public List<Invite> receiveInvites(String recipient) {
        return sqlSession.selectList(namespace+".receiveInvites",recipient);
    }

    @Override
    public int updateInvite(Invite invite) {
        return sqlSession.update(namespace + ".updateInvite",invite);
    }

}
