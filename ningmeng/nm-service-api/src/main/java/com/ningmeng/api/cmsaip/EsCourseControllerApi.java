package com.ningmeng.api.cmsaip;

import com.ningmeng.framework.domain.search.CourseSearchParam;
import com.ningmeng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;

/**
 * Created by Lenovo on 2020/2/28.
 */
@Api(value = "课程搜索",description = "课程搜索",tags = {"课程搜索"})
public interface EsCourseControllerApi {
    @ApiOperation("课程搜索")
    public QueryResponseResult list(int page, int size, CourseSearchParam courseSearchParam) throws IOException;

}
