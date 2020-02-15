package com.ningmeng.api.cmsaip;

import com.ningmeng.framework.domain.cms.CmsPage;
import com.ningmeng.framework.domain.cms.request.CmsPageResult;
import com.ningmeng.framework.domain.cms.request.QueryPageRequest;
import com.ningmeng.framework.model.response.QueryResponseResult;
import com.ningmeng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * Created by Lenovo on 2020/2/11.
 */
@Api(value = "cms页面管理接口",description = "描述，cms管理接口，提供页面增删改查")
public interface CmsPageControllerApi {
    //分页条件查询页面
    @ApiOperation("分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页数",required = true,paramType = "path",dataType = "int"),
            @ApiImplicitParam(name = "size",value = "每页条数",required = true,paramType = "path",dataType = "int")
    })
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);
    //添加页面
    @ApiOperation("添加页面")
    public CmsPageResult add(CmsPage cmsPage);
    //通过ID查询页面
    @ApiOperation("通过ID查询页面")
    public CmsPage findById(String id);
    //修改页面
    @ApiOperation("修改")
    public CmsPageResult edit(String id,CmsPage cmsPage);
    //删除
    @ApiOperation("删除")
    public ResponseResult delete(String id);
}
