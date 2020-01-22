package com.itheima.service;

import java.util.List;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;

public interface CheckGroupService {

	CheckGroup findById(Integer id);

	void edit(CheckGroup checkGroup, Integer[] checkItemId);

	void dels(Integer id);

	PageResult pageQuery(QueryPageBean queryPageBean);

	void add(CheckGroup checkGroup, Integer[] checkItemId);

	List<Integer> findCheckItemIdsAndCheckGroupId(Integer id);

	List<CheckGroup> findAll();
	
}
