package com.itheima.service;

import java.util.List;
import java.util.Map;

import com.itheima.pojo.OrderSetting;

public interface OrderSettingService {

	void add(List<OrderSetting> list);

	List<Map> getOrderSettingByMonth(String date);

	void editNumberByDate(OrderSetting orderSetting);

}
