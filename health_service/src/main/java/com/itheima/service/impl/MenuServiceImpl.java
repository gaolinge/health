package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.MenuDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Menu;
import com.itheima.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service(interfaceClass = MenuService.class)
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Override
    public void addMenu(Menu menu) {
        menuDao.addMenu(menu);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        Page<Menu> page = menuDao.findPage(queryPageBean.getQueryString());

        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void deleteMenu(Integer id) {

        int count = menuDao.findMenuAndRole(id);

        if (count <= 0) {

            menuDao.deleteMenu(id);

        }else{

            throw new RuntimeException("该菜单项被关联...");
        }

    }

    @Override
    public Menu findMenuById(Integer id) {
        return menuDao.findMenuById(id);
    }

    @Override
    public void updateMenu(Menu menu) {

        menuDao.updateMenu(menu);
    }

    @Override
    public List<Menu> findAll() {
        return menuDao.findAll();
    }
}
