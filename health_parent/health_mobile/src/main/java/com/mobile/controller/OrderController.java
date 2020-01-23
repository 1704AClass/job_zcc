package com.mobile.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import com.itheima.service.OrderSettingService;
import com.itheima.untils.SMSUtils;


@RequestMapping("/order")
@RestController
public class OrderController {
	@Reference
	private OrderService orderService;
	@Autowired
	private RedisTemplate redisTemplate;
	
	@RequestMapping("/submit")
	public Result submitOrder(@RequestBody Map map){
		String telephone = (String) map.get("telephone");
		
		//从Redis中获取缓存的验证码，key为手机号
		String codeInRedis = (String) redisTemplate.boundHashOps("SmsTelephone").get(MessageConstant.SEND_VALIDATECODE_SUCCESS);
		System.out.println(codeInRedis);
		String validateCode = (String) map.get("validateCode");
		System.out.println(validateCode);
		//校验手机验证码
		if(codeInRedis == null || !codeInRedis.equals(validateCode)){
			return new Result(false, MessageConstant.VALIDATECODE_ERROR);
		}
		Result result =null;
		//调用体检预约服务
		try{
			map.put("orderType", Order.ORDERTYPE_WEIXIN);
			result = orderService.order(map);
		}catch (Exception e){
			e.printStackTrace();
			//预约失败
			return result;
		}
		if(result.isFlag()){
			//预约成功，发送短信通知
			String orderDate = (String) map.get("orderDate");
			try {
				SMSUtils.sendShortMessage(telephone, orderDate, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	@RequestMapping("/findById")
	public Result findById(Integer id) {
		try{
			Map map = orderService.findById(id);
			//查询预约信息成功
			return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
		}catch (Exception e){
			e.printStackTrace();
			//查询预约信息失败
			return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
		}
	}
}
