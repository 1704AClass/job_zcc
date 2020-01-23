package com.itheima.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.mapper.SetMealMapper;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetMealService;

@Service
public class SetmealServiceImpl implements SetMealService{
	@Autowired
	private SetMealMapper setMealMapper;
	
	//添加   setmeal checkgroup多对多
	public void add(Setmeal setmeal, Integer[] checkgroupIds) {
		// TODO Auto-generated method stub
		setMealMapper.add(setmeal);
		if(checkgroupIds!=null&&checkgroupIds.length>0){
			setSetmealAndCheckGroup(setmeal.getId(),checkgroupIds);
		}
	}

	private void setSetmealAndCheckGroup(Integer id, Integer[] checkgroupIds) {
		// TODO Auto-generated method stub
		for (Integer checkgroupId : checkgroupIds) {
			Map<String,Integer> map=new HashMap();
			map.put("setmeal_id", id);
			map.put("checkgroup_id", checkgroupId);
			setMealMapper.setSetmealAndCheckGroup(map);
		}
	}

	//分页
	public PageResult pageQuery(QueryPageBean queryPageBean) {
		Integer currentPage = queryPageBean.getCurrentPage();
    	Integer pageSize = queryPageBean.getPageSize();
    	String queryString = queryPageBean.getQueryString();
    	PageHelper.startPage(currentPage, pageSize);
    	 Page<Setmeal> page = setMealMapper.findByCondition(queryString);
         return new PageResult(page.getTotal(),page.getResult());
	}
	

}
