package com.ningmeng.api.ucenter;

import com.ningmeng.framework.domain.ucenter.ext.NmUserExt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by Lenovo on 2020/3/11.
 */
@Api(value = "用户中心",description = "用户中心管理")
public interface UcenterControllerApi {
    @ApiOperation("查询用户信息")
    public NmUserExt getUserext(String username);
}
