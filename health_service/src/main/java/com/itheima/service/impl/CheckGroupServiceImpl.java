package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;


@Transactional
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    public void addCheckGroup(CheckGroup checkGroup, Integer[] checkitemIds) {

        //先保存检查组,并获取检查组id
        checkGroupDao.saveCheckGroup(checkGroup);

        //保存中间表数据
        HashMap<String,Object> map = new HashMap<>();
        map.put("checkGroupId",checkGroup.getId());
        for (Integer checkitemId : checkitemIds) {
            map.put("checkitemId",checkitemId);
            checkGroupDao.saveCheckGroupAndCheckItem(map);
        }
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        Page<CheckGroup> page = checkGroupDao.findCheckGroupByKeyword(queryPageBean.getQueryString());

        return new PageResult(page.getTotal(),page.getResult());

    }

    @Override
    public void deleteCheckGroup(Integer id) {
        //查询是否又关联
        int count = checkGroupDao.findCheckGroupAndItem(id);
        //是否删除
        if (count <= 0) {

            checkGroupDao.deleteCheckGroup(id);

        }else{

            throw new RuntimeException("该检查组被关联了...");
        }
    }

    @Override
    public CheckGroup findCheckGroup(Integer id) {

        return checkGroupDao.findCheckGroup(id);
    }

    @Override
    public List<Integer> findCheckItemIds(Integer id) {
        return checkGroupDao.findCheckItemIds(id);
    }

    @Override
    public void updateCheckGroup(CheckGroup checkGroup, Integer[] checkitemIds) {

        checkGroupDao.deleteCheckGroupAndItem(checkGroup.getId());

        HashMap<String,Object> map = new HashMap<>();
        map.put("checkGroupId",checkGroup.getId());
        for (Integer checkitemId : checkitemIds) {
            map.put("checkitemId",checkitemId);
            checkGroupDao.saveCheckGroupAndCheckItem(map);
        }

        checkGroupDao.updateCheckGroup(checkGroup);
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }

}
