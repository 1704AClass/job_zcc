package com.ningmeng.api.cmsaip;

import com.ningmeng.framework.model.request.GetMediaResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by Lenovo on 2020/3/7.
 */
@Api(value = "录播课程学习管理",description = "录播课程学习管理")
public interface CourseLearningControllerApi {
    @ApiOperation("获取课程学习地址")
    public GetMediaResult getmedia(String courseId, String teachplanId);
}
