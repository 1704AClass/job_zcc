package com.ningmeng.framework.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Lenovo on 2020/2/26.
 * 一键发布页面异常处理类
 */
@Data
@NoArgsConstructor//无参构造器注解
public class CmsPostPageResult extends ResponseResult {
    String pageUrl;
    public CmsPostPageResult(ResultCode resultCode,String pageUrl) {
        super(resultCode);
        this.pageUrl = pageUrl;
    }
}
