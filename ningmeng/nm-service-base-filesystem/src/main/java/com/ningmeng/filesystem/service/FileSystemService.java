package com.ningmeng.filesystem.service;

import com.alibaba.fastjson.JSON;
import com.ningmeng.filesystem.dao.FileSystemRepository;
import com.ningmeng.framework.domain.filesystem.FileSystem;
import com.ningmeng.framework.domain.filesystem.response.FileSystemCode;
import com.ningmeng.framework.domain.filesystem.response.UploadFileResult;
import com.ningmeng.framework.exception.ExceptionCast;
import com.ningmeng.framework.model.response.CommonCode;
import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Created by Lenovo on 2020/2/21.
 */
@Service
public class FileSystemService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileSystemService.class);
    @Value("${ningmeng.fastdfs.tracker_servers}")
    String tracker_servers;
    @Value("${ningmeng.fastdfs.connect_timeout_in_seconds}")
    int connect_timeout_in_seconds;
    @Value("${ningmeng.fastdfs.network_timeout_in_seconds}")
    int network_timeout_in_seconds;
    @Value("${ningmeng.fastdfs.charset}")
    String charset;
    @Autowired
    FileSystemRepository fileSystemRepository;
    //加载fdfs的配置
    private void initFdfsConfig(){
        try {
            ClientGlobal.initByTrackers(tracker_servers);
            ClientGlobal.setG_connect_timeout(connect_timeout_in_seconds);
            ClientGlobal.setG_network_timeout(network_timeout_in_seconds);
            ClientGlobal.setG_charset(charset);
        } catch (Exception e) {
            e.printStackTrace();
//初始化文件系统出错
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_SERVERFAIL);
        }
    }
    //上传文件
    public UploadFileResult upload(MultipartFile file,
                                   String filetag,
                                   String businesskey,
                                   String metadata){
        String file_id = fdfs_upload(file);
        if(file_id == null){
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_FILEISNULL);
        }
        //上传文件到fdfsString fileId = fdfs_upload(file);
        //创建文件信息对象
        FileSystem fileSystem = new FileSystem();
        //文件id
        fileSystem.setFileId(file_id);
        //文件在文件系统中的路径
        fileSystem.setFilePath(file_id);
        //业务标识
        fileSystem.setBusinesskey(businesskey);
        //标签
        fileSystem.setFiletag(filetag);
        //元数据
        if(StringUtils.isNotEmpty(metadata)){
            try {
                Map map = JSON.parseObject(metadata, Map.class);
                fileSystem.setMetadata(map);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //名称
        fileSystem.setFileName(file.getOriginalFilename());
        //大小
        fileSystem.setFileSize(file.getSize());
        //文件类型
        fileSystem.setFileType(file.getContentType());
        fileSystemRepository.save(fileSystem);
        return new UploadFileResult(CommonCode.SUCCESS,fileSystem);
    }
    //上传文件到fdfs，返回文件id
    public String fdfs_upload(MultipartFile file) {
        try {
            //加载fdfs的配置
            initFdfsConfig();
            //创建tracker client
            TrackerClient trackerClient = new TrackerClient();
            //获取trackerServer
            TrackerServer trackerServer = trackerClient.getConnection();
            //获取storage
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
            //创建storage client
            StorageClient1 storageClient1 = new StorageClient1(trackerServer,storeStorage);
            //上传文件
            //文件字节
            byte[] bytes = file.getBytes();
            //文件原始名称
            String originalFilename = file.getOriginalFilename();
            //文件扩展名
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            System.err.print(extName+"+extName");
            //文件id
            String file_id = storageClient1.upload_file1(bytes, extName, null);
            return file_id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}