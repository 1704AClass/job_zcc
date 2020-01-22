package com.itheima.service;

import java.util.List;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;

public interface CheckItemService {

	void add(CheckItem checkItem);

	List<CheckItem> findAll();

	CheckItem findById(Integer id);

	PageResult pageQuery(QueryPageBean queryPageBean);

	void delete(Integer id);

	void dels(Integer[] ids);

	void edit(CheckItem checkItem);

}
