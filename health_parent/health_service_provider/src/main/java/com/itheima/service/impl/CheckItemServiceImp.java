package com.itheima.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.mapper.CheckItemMapper;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;

@Service
public class CheckItemServiceImp implements CheckItemService{
	@Autowired
	private CheckItemMapper	checkItemMapper;
	
	//添加
	public void add(CheckItem checkItem) {
		checkItemMapper.add(checkItem);
		
	}
	//查看所有
	public List<CheckItem> findAll() {
		return checkItemMapper.findAll();
	}
	public CheckItem findById(Integer id) {
		return checkItemMapper.findById(id);
	}
	public PageResult pageQuery(QueryPageBean queryPageBean) {
		// TODO Auto-generated method stub
		//当前页
		Integer currentPage = queryPageBean.getCurrentPage();
		//总条数
		Integer pageSize = queryPageBean.getPageSize();
		//模糊
		String queryString = queryPageBean.getQueryString();
		//拼接字符串
		PageHelper.startPage(currentPage,pageSize);
		//page对象的数据
		Page<CheckItem> page = checkItemMapper.pageQuery(queryString);
		long total = page.getTotal();
		List<CheckItem> rows = page.getResult();
		return new PageResult(total, rows);
	}
	public void delete(Integer id) {
		checkItemMapper.deleteAssocication(id);
		checkItemMapper.dels(id);
	}
	public void dels(Integer[] ids) {
		for (Integer id : ids) {
			checkItemMapper.deleteAssocication(id);
			checkItemMapper.dels(id);
		}
	}
	public void edit(CheckItem checkItem) {
		checkItemMapper.edit(checkItem);
	}

}
