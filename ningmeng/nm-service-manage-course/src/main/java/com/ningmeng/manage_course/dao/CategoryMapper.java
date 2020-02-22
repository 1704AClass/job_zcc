package com.ningmeng.manage_course.dao;

import com.ningmeng.framework.domain.course.ext.CategoryNode;

/**
 * Created by Lenovo on 2020/2/20.
 */
public interface CategoryMapper {
    CategoryNode findList(String id);
}
