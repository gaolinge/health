package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckItemDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public void addCheckItem(CheckItem checkItem) {
        checkItemDao.saveCheckItem(checkItem);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        Page<CheckItem> page =  checkItemDao.findCheckItemByKeyword(queryPageBean.getQueryString());

        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void deleteCheckItem(Integer id) {

        //查询检查项是否被关联(count 是否为零)
        int count = checkItemDao.findCheckItemCount(id);

        //int i = 10/0;

        //判断是否为零
        if (count <= 0) {

            checkItemDao.deleteCheckItemById(id);

        }else{

            //大于零则证明被关联
            throw new RuntimeException("该检查项被引用了..");
        }

    }

    @Override
    public CheckItem findCheckItemById(Integer id) {

        return checkItemDao.findCheckItemById(id);
    }

    @Override
    public void updateCheckItemById(CheckItem checkItem) {

        checkItemDao.updateCheckItemById(checkItem);
    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}
