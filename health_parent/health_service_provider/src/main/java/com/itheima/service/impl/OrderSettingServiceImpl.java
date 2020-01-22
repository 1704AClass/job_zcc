package com.itheima.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.mapper.OrderSettingMapper;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
@Service
public class OrderSettingServiceImpl implements OrderSettingService{
	@Autowired
	private OrderSettingMapper orderSettingMapper;
	
	public void add(List<OrderSetting> list) {
		if(list!=null && list.size()>0){
			for (OrderSetting orderSetting : list) {
				//检查此数据是否存在
				long count=orderSettingMapper.findCountByOrderDate(orderSetting.getOrderDate());
				if(count>0){
					//已经存在，执行更新操作
					orderSettingMapper.editNumberByOrderDate(orderSetting);
				}else{
					orderSettingMapper.add(orderSetting);
				}
			}
		}
	}
	//根据日期查询预约设置数据
	public List<Map> getOrderSettingByMonth(String date) {
		String dateBegin=date + "-1";
		String dateEnd=date + "-31";
		Map map=new HashMap();
		map.put("dataBegin",dateBegin);
		map.put("dateEnd",dateEnd);
		List<OrderSetting> list=orderSettingMapper.getOrderSettingByMonth(map);
		List<Map> data=new ArrayList();
		for (OrderSetting orderSetting : list) {
			Map orderSettingMap=new HashMap();
			//获得日期，号
			orderSettingMap.put("date", orderSetting.getOrderDate().getDate());
			//获得可预约人数
			orderSettingMap.put("number", orderSetting.getNumber());
			//已预约人数
			data.add(orderSettingMap);
		}
		return data;
	}
	public void editNumberByDate(OrderSetting orderSetting) {
		long count = orderSettingMapper.findCountByOrderDate(orderSetting.getOrderDate());
		if(count > 0){
			//当前日期已经进行了预约设置，需要进行修改操作
			orderSettingMapper.editNumberByOrderDate(orderSetting);
		}else{ 
			//当前日期没有进行预约设置，进行添加操作
			orderSettingMapper.add(orderSetting);
		}
	}

}
