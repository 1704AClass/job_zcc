package com.ningmeng.manage_media.controller;

import com.ningmeng.api.cmsaip.MediaFileControllerApi;
import com.ningmeng.framework.domain.media.request.QueryMediaFileRequest;
import com.ningmeng.framework.model.response.QueryResponseResult;
import com.ningmeng.manage_media.service.MediaFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Lenovo on 2020/3/6.
 */
@RestController
@RequestMapping("/media/file")
public class MediaFileController implements MediaFileControllerApi{
    @Autowired
    private MediaFileService mediaFileService;

    @Override
    @PostMapping("/findList/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page,@PathVariable("size") int size,@RequestBody QueryMediaFileRequest queryMediaFileRequest) {
        //媒资文件查询
        return  mediaFileService.findList(page, size, queryMediaFileRequest);
    }
}
