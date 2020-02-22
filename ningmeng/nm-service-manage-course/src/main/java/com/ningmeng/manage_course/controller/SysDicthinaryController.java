package com.ningmeng.manage_course.controller;

import com.ningmeng.api.cmsaip.SysDicthinaryControllerApi;
import com.ningmeng.framework.domain.system.SysDictionary;
import com.ningmeng.manage_course.service.SysDicthinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Lenovo on 2020/2/20.
 */
@RestController
@RequestMapping("/SysDicthinary")
public class SysDicthinaryController implements SysDicthinaryControllerApi {
   @Autowired
   SysDicthinaryService sysDicthinaryService;
    @Override
    @GetMapping("/getByType/{type}")
    public SysDictionary getByType(String type) {
        return sysDicthinaryService.getByType(type);
    }
}
