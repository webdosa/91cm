package com.nineone.nocm.repository;

import com.nineone.nocm.domain.ContentsFile;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FileStorage {

    @Autowired
    private SqlSessionTemplate sqlSession;

    private String nmaespace = "com.nineone.nocm.mapper.file";

    public int saveFile(ContentsFile file){
        return sqlSession.insert(nmaespace + ".saveFile",file);
    }
}
