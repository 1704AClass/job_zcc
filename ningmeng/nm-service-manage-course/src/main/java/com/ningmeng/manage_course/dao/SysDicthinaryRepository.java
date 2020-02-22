package com.ningmeng.manage_course.dao;

import com.ningmeng.framework.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Lenovo on 2020/2/20.
 */
public interface SysDicthinaryRepository extends MongoRepository<SysDictionary,String> {
    SysDictionary findByDType(String type);
}
