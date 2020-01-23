package com.itheima.service;

import java.util.List;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;

public interface SetMealService {

	void add(Setmeal setmeal, Integer[] checkgroupIds);

	PageResult pageQuery(QueryPageBean queryPageBean);



}
