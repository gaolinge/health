package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;

import java.util.List;

public interface SetmealService {

    PageResult findPage(QueryPageBean queryPageBean);

    Setmeal findSetmeal(Integer id);

    List<Integer> findCheckGroupIds(Integer id);

    void deletSetmeal(Integer id);

    void addSetmeal(Setmeal setmeal, Integer[] checkgroupIds);

    void updateSetmeal(Setmeal setmeal, Integer[] checkgroupIds);

    List<Setmeal> getSetmeal();

    Setmeal findById(Integer id);
}
