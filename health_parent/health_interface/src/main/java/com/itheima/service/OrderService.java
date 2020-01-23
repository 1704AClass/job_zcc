package com.itheima.service;

import java.util.Map;

import com.itheima.entity.Result;

public interface OrderService {

	Result order(Map map) throws Exception;

	Map findById(Integer id);

}
