package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {
    void addCheckGroup(CheckGroup checkGroup, Integer[] checkitemIds);

    PageResult findPage(QueryPageBean queryPageBean);

    void deleteCheckGroup(Integer id);

    CheckGroup findCheckGroup(Integer id);

    List<Integer> findCheckItemIds(Integer id);

    void updateCheckGroup(CheckGroup checkGroup, Integer[] checkitemIds);

    List<CheckGroup> findAll();


}
