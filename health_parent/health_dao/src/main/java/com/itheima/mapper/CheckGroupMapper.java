package com.itheima.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;

public interface CheckGroupMapper {

	Page<CheckGroup> pageQuery(String queryString);
	
	void add(CheckGroup checkGroup);

	void updateCheckGroupIdAndCheckitemId(Map map);

	void dels(Integer id);
	@Delete("delete from t_checkgroup_checkitem where checkgroup_id=#{id}")
	void delsCheckGroupIdAndCheckitemId(Integer id);

	CheckGroup findById(Integer id);
	
	List<Integer> findCheckItemIdsAndCheckGroupId(Integer id);
	
	void edit(CheckGroup checkGroup);

	void deleteAssocication(Integer id);

	List<CheckGroup> findAll();

}
