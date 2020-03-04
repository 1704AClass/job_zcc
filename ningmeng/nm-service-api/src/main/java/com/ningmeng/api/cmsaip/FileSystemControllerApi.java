package com.ningmeng.api.cmsaip;

import com.ningmeng.framework.domain.filesystem.response.UploadFileResult;
import io.swagger.annotations.Api;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Lenovo on 2020/2/21.
 */
@Api(value = "上传图片",description = "提供图片上传到了fastDFS")
public interface FileSystemControllerApi {
    /**
     * 上传文件
     * @param multipartFile 文件
     * @param filetag 文件标签
     * @param businesskey 业务key
     * @param
     * @return
     */
    public UploadFileResult upload(MultipartFile multipartFile,
                                   //文件类型
                                  String filetag,
                                   //业务Key
                                   String businesskey,
                                   //文件元信息
                                   String metadata );
}
