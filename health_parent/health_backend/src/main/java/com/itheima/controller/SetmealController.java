package com.itheima.controller;

import java.io.IOException;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetMealService;
import com.itheima.untils.AliyunOSSClientUtils;
import com.itheima.untils.QiniuUtils;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
	@Reference
	private SetMealService setmealService;
	
	//图片上传
	@RequestMapping("/upload")
	/*public Result upload(MultipartFile imgFile){
		AliyunOSSClientUtils aliyunOSSClientUtils = new AliyunOSSClientUtils();
		try {
			String tag = aliyunOSSClientUtils.uploadImg2Oss(imgFile);
			String imgUrl = aliyunOSSClientUtils.getImgUrl(tag);
			Result result = new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Result result = new Result(true, MessageConstant.PIC_UPLOAD_FAIL);
		return result;
	}*/
	public Result upload(@RequestParam("imgFile") MultipartFile imgFile){
		try {
			//获取原始文件名
			String originalFilename = imgFile.getOriginalFilename();
			int lastIndexOf = originalFilename.lastIndexOf(".");
			//获取文件后缀
			String suffix = originalFilename.substring(lastIndexOf-1);
			//使用UUID随机产生文件名称，防止同名文件覆盖
			String fileName=UUID.randomUUID().toString()+suffix;
			QiniuUtils.upload2Qiniu(imgFile.getBytes(), fileName);
			//图片上传成功
			Result result = new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS);
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//图片上传失败
			Result result = new Result(true, MessageConstant.PIC_UPLOAD_FAIL);
			return result;
		}
	}
	//新增套餐
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){
        try{
            setmealService.add(setmeal,checkgroupIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    //分页查询
   @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return setmealService.pageQuery(queryPageBean);
    }
}
