package com.itheima.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.itheima.pojo.OrderSetting;

public interface OrderSettingMapper {

	long findCountByOrderDate(Date orderDate);

	void editNumberByOrderDate(OrderSetting orderSetting);

	void add(OrderSetting orderSetting);

	List<OrderSetting> getOrderSettingByMonth(Map date);

	OrderSetting findByOrderDate(Date date);

	void editReservationsByOrderDate(OrderSetting orderSetting);

}
