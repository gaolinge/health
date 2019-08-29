package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.PermissionDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Menu;
import com.itheima.pojo.Permission;
import com.itheima.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Transactional
@Service(interfaceClass = PermissionService.class)
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public Set<Permission> findPermissionByRid(Integer id) {
        return permissionDao.findPermissionByRid(id);
    }

    @Override
    public void addPermission(Permission permission) {
        permissionDao.addPermission(permission);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        Page<Permission> page = permissionDao.findPage(queryPageBean.getQueryString());

        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void deletePermission(Integer id) {

        int count = permissionDao.findPermissionAndRole(id);

        if (count <= 0) {

            permissionDao.deletePermission(id);

        }else{

            throw new RuntimeException("该菜单项被关联...");
        }
    }

    @Override
    public Permission findPermissionById(Integer id) {

        return permissionDao.findPermissionById(id);
    }

    @Override
    public void updatePermission(Permission permission) {

        permissionDao.updatePermission(permission);
    }

    @Override
    public List<Permission> findAll() {

        return permissionDao.findAll();
    }
}
