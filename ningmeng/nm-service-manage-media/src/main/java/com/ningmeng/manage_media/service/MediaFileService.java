package com.ningmeng.manage_media.service;

import com.ningmeng.framework.domain.media.MediaFile;
import com.ningmeng.framework.domain.media.request.QueryMediaFileRequest;
import com.ningmeng.framework.model.response.CommonCode;
import com.ningmeng.framework.model.response.QueryResponseResult;
import com.ningmeng.framework.model.response.QueryResult;
import com.ningmeng.manage_media.dao.MediaFileRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

/**
 * Created by Lenovo on 2020/3/6.
 */
@Service
public class MediaFileService {
    private static Logger logger = LoggerFactory.getLogger(MediaFileService.class);
    @Autowired
    MediaFileRepository mediaFileRepository;
    //文件列表查询从mongdb中获取，分页模糊查询
    public QueryResponseResult findList(int page, int size, QueryMediaFileRequest queryMediaFileRequest){
        //查询条件
        MediaFile mediaFile = new MediaFile();
        if(queryMediaFileRequest==null){
            queryMediaFileRequest=new QueryMediaFileRequest();
        }
        //查询条件匹配器
        ExampleMatcher matching = ExampleMatcher.matching()
                .withMatcher("tag",ExampleMatcher.GenericPropertyMatchers.contains())//tag字段模糊查询
                .withMatcher("fileOriginalName", ExampleMatcher.GenericPropertyMatchers.contains())//文件原始名模糊查询
                .withMatcher("processStatus", ExampleMatcher.GenericPropertyMatchers.exact());//处理状态精确匹配
        //查询条件对象
        //查询条件对象
        if(StringUtils.isNotEmpty(queryMediaFileRequest.getTag())){
            mediaFile.setTag(queryMediaFileRequest.getTag());
        }
        if(StringUtils.isNotEmpty(queryMediaFileRequest.getFileOriginalName())){
            mediaFile.setFileOriginalName(queryMediaFileRequest.getFileOriginalName());
        }
        if(StringUtils.isNotEmpty(queryMediaFileRequest.getProcessStatus())){
            mediaFile.setProcessStatus(queryMediaFileRequest.getProcessStatus());
        }
        //定义example实例
        Example<MediaFile> ex = Example.of(mediaFile, matching);
        //mongdb的分页是从1开始的
        page=page-1;
        //分页参数
        Pageable pageable = new PageRequest(page, size);

        //分页查询
        Page<MediaFile> all = mediaFileRepository.findAll(ex, pageable);
        //用于分页，有数据列表和总条数
        QueryResult<MediaFile> mediaFileQueryResult = new QueryResult<MediaFile>();
        mediaFileQueryResult.setList(all.getContent());
        mediaFileQueryResult.setTotal(all.getTotalElements());
        return  new QueryResponseResult(CommonCode.SUCCESS,mediaFileQueryResult);
    }
}
