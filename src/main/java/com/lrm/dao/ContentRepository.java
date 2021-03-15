package com.lrm.dao;

import com.lrm.po.Content;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @Describe: MongoDB的数据访问层
 * @Author: tyf
 * @CreateTime: 2021/3/15
 **/

@Repository
public interface ContentRepository extends MongoRepository<Content, String> {
}
