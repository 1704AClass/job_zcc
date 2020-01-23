package com.itheima.service;

import java.util.List;

import com.itheima.pojo.Setmeal;

public interface MoblieService {
	List<Setmeal> findAll();
	Setmeal findByid(int id);
}
