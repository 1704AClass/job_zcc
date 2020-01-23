package com.itheima.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.mapper.SetMealMapper;
import com.itheima.pojo.Setmeal;
import com.itheima.service.MoblieService;
@Service
public class MobileServiceImpl implements MoblieService{
	@Autowired
	private SetMealMapper setMealMapper;
	//day6的列表   t_setmeal 预约
	public List<Setmeal> findAll() {
		List<Setmeal> list = setMealMapper.findAll();
		System.out.println(list);
		return list;
	}

	public Setmeal findByid(int id) {
		// TODO Auto-generated method stub
		return setMealMapper.findByid(id);
	}
}
