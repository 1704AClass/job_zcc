package com.ningmeng.manage_cms.service;

import com.ningmeng.framework.domain.cms.CmsPage;
import com.ningmeng.framework.domain.cms.request.CmsPageResult;
import com.ningmeng.framework.domain.cms.request.QueryPageRequest;
import com.ningmeng.framework.exception.ExceptionCast;
import com.ningmeng.framework.model.response.*;
import com.ningmeng.manage_cms.dao.CmsPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * Created by Lenovo on 2020/2/11.
 */
@Service
public class PageService {
    @Autowired
    CmsPageRepository cmsPageRepository;

    //分页查询

    /**
     *
     * @param page
     * @param size
     * @param queryPageRequest 自定义页面属性  查询条件
     * @return
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {
        //条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        exampleMatcher = exampleMatcher.withMatcher("pageAlise", ExampleMatcher.GenericPropertyMatchers.contains());
        //条件值
        CmsPage cmsPage = new CmsPage();
        //站点ID
       if (!StringUtils.isEmpty(queryPageRequest.getSiteId())){
           cmsPage.setSiteId(queryPageRequest.getSiteId());
       }
       //页面别名
        if (!StringUtils.isEmpty(queryPageRequest.getPageAliase())){
           cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        //创建条件实例
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);

        if (queryPageRequest == null) {
            queryPageRequest = new QueryPageRequest();
        }
        if (page <= 0) {
            page = 1;
        }
        page = page - 1;
        if (size <= 0) {
            size = 20;
        }
        //分页对象
       Pageable pageable= PageRequest.of(page,size);
        //分页查询
      /*  Page<CmsPage> all = cmsPageRepository.findAll(pageable);*/
        Page<CmsPage> all = cmsPageRepository.findAll(example,pageable);
        QueryResult<CmsPage> cmsPageQueryResult = new QueryResult<>();
        cmsPageQueryResult.setList(all.getContent());
        cmsPageQueryResult.setTotal(all.getTotalElements());
        return new QueryResponseResult(CommonCode.SUCCESS,cmsPageQueryResult);
    }
    //添加页面+异常处理
    public CmsPageResult add(CmsPage cmsPage) {
        //判断页面是否存在，根据站点id,页面名称，页面webpath
        CmsPage cmsPage1 = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if (cmsPage1 != null) {
            //抛出异常
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTS);
        }
        //页面主键id由spring data自动生成
        cmsPage.setPageId(null);
        cmsPageRepository.save(cmsPage);
        //返回结果
        CmsPageResult cmsPageResult = new CmsPageResult(CommonCode.SUCCESS, cmsPage);
        return  cmsPageResult;

    }
    //根据id查询页面
    public CmsPage getById(String id){
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if(optional.isPresent()){
            return  optional.get();
        }
        return  null;
    }
    //更新页面信息
    public CmsPageResult update(String id,CmsPage cmsPage){
        //根据id查询信息（this?）
        CmsPage one = this.getById(id);
        if(one!=null){
            //更新数据
            one.setTemplateId(cmsPage.getTemplateId());
            one.setSiteId(cmsPage.getSiteId());
            one.setPageAliase(cmsPage.getPageAliase());
            one.setPageName(cmsPage.getPageName());
            one.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            one.setPageWebPath(cmsPage.getPageWebPath());
            one.setPageAliase(cmsPage.getPageAliase());
            CmsPage save = cmsPageRepository.save(one);
            if (save!=null){
                //返回成功
                return  new CmsPageResult(CommonCode.SUCCESS,save);
            }
        }
        //返回失败
        return  new CmsPageResult(CommonCode.FAIL,null);
    }
    //删除
    public ResponseResult delete(String id){
        CmsPage one = this.getById(id);
        if (one!=null){
            cmsPageRepository.deleteById(id);
            return  new ResponseResult(CommonCode.SUCCESS);
        }
        return  new ResponseResult(CommonCode.FAIL);
    }
}
