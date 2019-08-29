package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Permission;

import java.util.List;
import java.util.Set;

public interface PermissionService {
    Set<Permission> findPermissionByRid(Integer id);

    void addPermission(Permission permission);

    PageResult findPage(QueryPageBean queryPageBean);

    void deletePermission(Integer id);

    Permission findPermissionById(Integer id);

    void updatePermission(Permission permission);

    List<Permission> findAll();
}
