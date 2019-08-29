package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Menu;

import java.util.List;

public interface MenuService {

    void addMenu(Menu menu);

    PageResult findPage(QueryPageBean queryPageBean);

    void deleteMenu(Integer id);

    Menu findMenuById(Integer id);

    void updateMenu(Menu menu);

    List<Menu> findAll();

}
