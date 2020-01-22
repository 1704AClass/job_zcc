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
import com.itheima.mapper.CheckGroupMapper;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;


@Service
public class CheckGroupServiceImpl implements CheckGroupService{

	@Autowired
	private CheckGroupMapper checkGroupmapper;
	
	public CheckGroup findById(Integer id) {
		// TODO Auto-generated method stub
		return checkGroupmapper.findById(id);
	}

	public void dels(Integer id) {
		// TODO Auto-generated method stub
		checkGroupmapper.delsCheckGroupIdAndCheckitemId(id);
		checkGroupmapper.dels(id);
	}

	public PageResult pageQuery(QueryPageBean queryPageBean) {
		// TODO Auto-generated method stub
		//获取当前页
		Integer currentPage = queryPageBean.getCurrentPage();
		//获取模糊参数
		String queryString = queryPageBean.getQueryString();
		//获取同条数
		Integer pageSize = queryPageBean.getPageSize();
		//拼接字符串
		PageHelper.startPage(currentPage, pageSize);
		//查Page对象的集合
		Page<CheckGroup> page = checkGroupmapper.pageQuery(queryString);
		//获取总条数
		long total = page.getTotal();
		//获取查出的集合
		List<CheckGroup> rows = page.getResult();
		//利用封装的对象返回一个集合集
		return new PageResult(total, rows);
	}
	
	public void add(CheckGroup checkGroup, Integer[] checkItemId) {
		// TODO Auto-generated method stub
		//建立新的检查组
		checkGroupmapper.add(checkGroup);
		//用mybaits获取主键自动生成的id
		Integer checkGroupId = checkGroup.getId();
		this.updateCheckGroupIdAndCheckitemId(checkGroupId, checkItemId);
	}

	public List<Integer> findCheckItemIdsAndCheckGroupId(Integer id) {
		// TODO Auto-generated method stub
		return checkGroupmapper.findCheckItemIdsAndCheckGroupId(id);
	}

	public void edit(CheckGroup checkGroup, Integer[] checkItemId) {
		// TODO Auto-generated method stub
		checkGroupmapper.edit(checkGroup);
		checkGroupmapper.deleteAssocication(checkGroup.getId());
		Integer checkGroupId = checkGroup.getId();
		//用比较
		this.updateCheckGroupIdAndCheckitemId(checkGroupId, checkItemId);
	}
	
	//共同方法抽取
	public void updateCheckGroupIdAndCheckitemId(Integer checkGroupId,Integer[] checkItemId) {
		if(checkGroupId!=null && checkItemId!=null && checkItemId.length>0){
			//循环前台传过来的数组
			for (Integer checkitemId : checkItemId) {
				//因为要传2个值，所以利用咱们的map
				Map map=new HashMap();
				map.put("checkGroupId", checkGroupId);
				map.put("checkitemId", checkitemId);
				//去后台对中间表进行操作
				checkGroupmapper.updateCheckGroupIdAndCheckitemId(map);
			}
		}
	}

	public List<CheckGroup> findAll() {
		// TODO Auto-generated method stub
		return checkGroupmapper.findAll();
	}
}
