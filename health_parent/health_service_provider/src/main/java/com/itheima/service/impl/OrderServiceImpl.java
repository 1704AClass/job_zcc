package com.itheima.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.mapper.MemberMapper;
import com.itheima.mapper.OrderMapper;
import com.itheima.mapper.OrderSettingMapper;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderService;
import com.itheima.untils.DateUtils;
@Service
public class OrderServiceImpl implements OrderService{
	@Autowired
	private OrderSettingMapper orderSettingMapper;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private OrderMapper orderMapper;
	
	public Result order(Map map) throws Exception {
		// TODO Auto-generated method stub
		String orderDate = (String) map.get("orderDate");
		
		Date date = DateUtils.parseString2Date(orderDate);
		
		OrderSetting orderSetting = orderSettingMapper.findByOrderDate(date);
		if(orderSetting == null){
			return new Result(false,MessageConstant.SELECTED_DATE_CANNOT_ORDER);
		}
		
		//检查预约日期是否预约已满
		int number = orderSetting.getNumber();//可预约人数
		int reservations = orderSetting.getReservations();//已预约人数
		if(reservations >= number){
			//预约已满，不能预约
			return new Result(false,MessageConstant.ORDER_FULL);
		}
		//检查当前用户是否为会员，根据手机号判断
		String telephone = (String) map.get("telephone");
		Member member = memberMapper.findByTelephone(telephone);
		//防止重复预约
		if(member != null){
			Integer memberId = member.getId();
			int setmealId = Integer.parseInt((String)map.get("setmealId"));
			Order order = new Order(memberId,date,null,null,setmealId);
			List<Order> list = orderMapper.findByCondition(order);
		if(list != null && list.size() > 0){
			//已经完成了预约，不能重复预约
			return new Result(false,MessageConstant.HAS_ORDERED);
		}
		}
		//可以预约，设置预约人数加一
		orderSetting.setReservations(orderSetting.getReservations()+1);
		orderSettingMapper.editReservationsByOrderDate(orderSetting);
		if(member == null){
			//当前用户不是会员，需要添加到会员表
			member = new Member();
			member.setName((String) map.get("name"));
			member.setPhoneNumber(telephone);
			member.setIdCard((String) map.get("idCard"));
			member.setSex((String) map.get("sex"));
			member.setRegTime(new Date());
			memberMapper.add(member);
		}
		//保存预约信息到预约表
		Order order = new Order(member.getId(),date,(String)map.get("orderType"),Order.ORDERSTATUS_NO,Integer.parseInt((String)map.get("setmealId")));
		orderMapper.add(order);
		return new Result(true,MessageConstant.ORDER_SUCCESS,order.getId());
	}
	public Map findById(Integer id){
		// TODO Auto-generated method stub
		Map map = orderMapper.findById4Detail(id);
		if(map != null){
			//处理日期格式
			Date orderDate = (Date) map.get("orderDate");
			try {
				map.put("orderDate",DateUtils.parseDate2String(orderDate));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return map;
	}

}
