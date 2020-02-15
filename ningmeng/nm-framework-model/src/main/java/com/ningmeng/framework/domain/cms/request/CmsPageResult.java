package com.ningmeng.framework.domain.cms.request;

import com.ningmeng.framework.domain.cms.CmsPage;
import com.ningmeng.framework.model.response.ResponseResult;
import com.ningmeng.framework.model.response.ResultCode;
import lombok.Data;

/**
 * Created by Lenovo on 2020/2/12.
 *
 */
@Data
public class CmsPageResult extends ResponseResult{
    CmsPage cmsPage;

    public CmsPageResult(ResultCode resultCode, CmsPage cmsPage) {
        super(resultCode);
        this.cmsPage = cmsPage;
    }
}
