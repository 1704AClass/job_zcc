package com.ningmeng.api.cmsaip;

import com.ningmeng.framework.domain.course.*;
import com.ningmeng.framework.domain.course.ext.TeachplanNode;
import com.ningmeng.framework.domain.course.request.CourseListRequest;
import com.ningmeng.framework.model.response.CoursePublishResult;
import com.ningmeng.framework.model.response.QueryResponseResult;
import com.ningmeng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by Lenovo on 2020/2/18.
 */
@Api(value = "课程管理course",description = "课程管理，管理接口，提供页面增删改查")
public interface CourseControllerApi {
    /**
     * 根据课程编号差列表
     * @param courseId
     * @return
     */
    @ApiOperation("课程计划查询")
    public TeachplanNode findTeachplanList(String courseId);
    @ApiOperation("添加课程")
    public ResponseResult  addTeachplan(Teachplan teachplan);

    //查询课程列表
    @ApiOperation("查询我的课程列表")
    public QueryResponseResult findCourseList(
            int page,
            int size,
            CourseListRequest courseListRequest
    );
    @ApiOperation("获取课程基础信息")
    public CourseBase getCourseBaseById(String courseId) throws RuntimeException;
    @ApiOperation("更新课程基础信息")
    public ResponseResult updateCourseBase(String id,CourseBase courseBase);
    @ApiOperation("获取课程营销信息")
    public CourseMarket getCourseMarketById(String courseId);
    @ApiOperation("更新课程营销信息")
    public ResponseResult updateCourseMarket(String id,CourseMarket courseMarket);
    @ApiOperation("添加课程图片")
    public ResponseResult addCoursePic(String courseId,String pic);
    @ApiOperation("根据课程ID查看图片")
    public CoursePic findCoursepic(String courseId);
    @ApiOperation("删除课程图片")
    public ResponseResult deleteCoursePic(String courseId);


    @ApiOperation("课程视图查询")
    public CourseView courseview(String id);
    @ApiOperation("预览课程")
    public CoursePublishResult preview(String id);
    @ApiOperation("发布课程")
    public CoursePublishResult publish(@PathVariable String id);


    @ApiOperation("保存媒资信息")
    public ResponseResult savemedia(TeachplanMedia teachplanMedia);

}
