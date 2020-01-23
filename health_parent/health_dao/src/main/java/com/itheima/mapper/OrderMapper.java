package com.itheima.mapper;

import com.itheima.pojo.Order;

import java.util.List;
import java.util.Map;

public interface OrderMapper {
	//新增
    public void add(Order order);
    //动态条件查询
    public List<Order> findByCondition(Order order);
    //根据预约id查询预约信息，包括体检人信息、套餐信息
    public Map findById4Detail(Integer id);
    //根据日期统计预约数
    public Integer findOrderCountByDate(String date);
    //根据日期统计预约数，统计指定日期之后的预约数
    public Integer findOrderCountAfterDate(String date);
    //根据日期统计到诊数
    public Integer findVisitsCountByDate(String date);
    //根据日期统计到诊数，统计指定日期之后的到诊数
    public Integer findVisitsCountAfterDate(String date);
    //热门套餐
    public List<Map> findHotSetmeal();
}
