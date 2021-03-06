package com.mobile.controller;

import java.util.List;

import org.springframework.data.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.MoblieService;
import com.itheima.service.SetMealService;

@RestController
@RequestMapping("/mobile")
public class MoblieController {
	@Reference
	private MoblieService mobileService;
	@RequestMapping("/getAllSetmeal")
	public Result getAllSetmeal(){
		try {
			List<Setmeal> list = mobileService.findAll();
			return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS, list);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,MessageConstant.GET_SETMEAL_LIST_FAIL);
		}
	}
	@RequestMapping("/findByid")
	private Result findByid(int id) {
		// TODO Auto-generated method stub
		try {
			Setmeal setmeal = mobileService.findByid(id);
			return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
		}
	}
}
