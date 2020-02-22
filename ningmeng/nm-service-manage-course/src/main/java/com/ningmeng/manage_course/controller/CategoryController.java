package com.ningmeng.manage_course.controller;

import com.ningmeng.api.cmsaip.CategoryControllerApi;
import com.ningmeng.framework.domain.course.ext.CategoryNode;
import com.ningmeng.manage_course.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Lenovo on 2020/2/20.
 */
@RestController
@RequestMapping("/category")
public class CategoryController implements CategoryControllerApi{

    @Autowired
    CategoryService categoryService;

    @Override
    @GetMapping("/findList/{id}")
    public CategoryNode findList(@PathVariable("id") String id) {
        return categoryService.findList(id);
    }
}
