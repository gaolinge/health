package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {
    void addCheckItem(CheckItem checkItem);

    PageResult findPage(QueryPageBean queryPageBean);

    void deleteCheckItem(Integer id);

    CheckItem findCheckItemById(Integer id);

    void updateCheckItemById(CheckItem checkItem);

    List<CheckItem> findAll();

}
