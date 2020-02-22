package com.ningmeng.manage_course.dao;

import com.github.pagehelper.Page;
import com.ningmeng.framework.domain.course.CourseBase;
import com.ningmeng.framework.domain.course.CourseMarket;
import com.ningmeng.framework.domain.course.ext.CourseInfo;
import com.ningmeng.framework.domain.course.request.CourseListRequest;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Administrator.
 */
@Mapper
public interface CourseMapper {
   CourseBase findCourseBaseById(String id);
   //根据公司编号查询列表，图片和课程名称
   Page<CourseInfo> findCourseListPage(CourseListRequest courseListRequest);

    CourseBase getCourseBaseById(String courseId);

    CourseMarket getCourseMarketById(String courseId);
}
