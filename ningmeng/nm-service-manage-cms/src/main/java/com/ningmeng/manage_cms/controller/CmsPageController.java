package com.ningmeng.manage_cms.controller;

import com.ningmeng.api.cmsaip.CmsPageControllerApi;
import com.ningmeng.framework.domain.cms.CmsPage;
import com.ningmeng.framework.domain.cms.request.CmsPageResult;
import com.ningmeng.framework.domain.cms.request.QueryPageRequest;
import com.ningmeng.framework.model.response.QueryResponseResult;
import com.ningmeng.framework.model.response.ResponseResult;
import com.ningmeng.manage_cms.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Lenovo on 2020/2/11.
 */
@RestController
@RequestMapping("/cms")
public class CmsPageController implements CmsPageControllerApi {
    @Autowired
    PageService pageService;

    //分页条件查询
    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page, @PathVariable("size") int size, QueryPageRequest queryPageRequest) {
         return pageService.findList(page,size,queryPageRequest);
    }
    //添加页面
    @Override
    @PostMapping("/add")
    public CmsPageResult add(@RequestBody CmsPage cmsPage) {
        return pageService.add(cmsPage);
    }
    //根据id查询页面
    @Override
    @GetMapping("/findById/{id}")
    public CmsPage findById(String id) {
        return pageService.getById(id);
    }
    //更新页面
    @Override
    @PostMapping("/edit/{id}")
    public CmsPageResult edit(@PathVariable("id") String id, @RequestBody CmsPage cmsPage) {
        return pageService.update(id,cmsPage);
    }
    //删除
    @Override
    @GetMapping("/delete/{id}")
    public ResponseResult delete(String id) {
        return pageService.delete(id);
    }


}
