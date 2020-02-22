package com.ningmeng.manage_course.service;

import com.ningmeng.framework.domain.system.SysDictionary;
import com.ningmeng.manage_course.dao.SysDicthinaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Lenovo on 2020/2/20.
 */
@Service
public class SysDicthinaryService {
    @Autowired
    SysDicthinaryRepository sysDicthinaryRepository;


    public SysDictionary getByType(String type) {
        return sysDicthinaryRepository.findByDType(type);
    }
}
