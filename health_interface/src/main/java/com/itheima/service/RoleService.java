package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    Set<Role> findRoleByUid(Integer id);

    void addRole(Role role, Integer[] menuIds, Integer[] permissionIds);

    PageResult findPage(QueryPageBean queryPageBean);

    void deleteRole(Integer id);

    Role findRole(Integer id);

    List<Integer> findMenuIds(Integer id);

    List<Integer> findPermissionIds(Integer id);

    void updateRole(Role role, Integer[] menuIds, Integer[] permissionIds);

    List<Role> findAll();
}
