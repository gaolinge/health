package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.RoleDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Role;
import com.itheima.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Set;


@Transactional
@Service(interfaceClass = RoleService.class)
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Set<Role> findRoleByUid(Integer id) {
        return roleDao.findRoleByUid(id);
    }

    @Override
    public void addRole(Role role, Integer[] menuIds, Integer[] permissionIds) {

        //先保存检查组,并获取检查组id
        roleDao.saveRole(role);

        //保存菜单中间表数据
        HashMap<String,Object> map = new HashMap<>();
        map.put("roleId",role.getId());
        for (Integer menuId : menuIds) {
            map.put("menuId",menuId);
            roleDao.saveRoleAndMenu(map);
        }

        //保存权限中间表数据
        for (Integer permissionId : permissionIds) {
            map.put("permissionId",permissionId);
            roleDao.saveRoleAndPermission(map);
        }
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        Page<Role> page = roleDao.findPage(queryPageBean.getQueryString());

        return new PageResult(page.getTotal(),page.getResult());

    }

    @Override
    public void deleteRole(Integer id) {

        //查询是否又关联
        int countMenu = roleDao.findRoleAndMenu(id);
        int countPermission = roleDao.findRolePermission(id);
        //是否删除
        if (countMenu>0||countPermission>0) {

            throw new RuntimeException("该角色被关联了...");

        }

        roleDao.deleteRole(id);

    }

    @Override
    public Role findRole(Integer id) {

        return roleDao.findRole(id);
    }

    @Override
    public List<Integer> findMenuIds(Integer id) {

        return roleDao.findMenuIds(id) ;
    }

    @Override
    public List<Integer> findPermissionIds(Integer id) {

        return roleDao.findPermissionIds(id);
    }

    @Override
    public void updateRole(Role role, Integer[] menuIds, Integer[] permissionIds) {
        roleDao.deleteRoleAndMenu(role.getId());
        roleDao.deleteRoleAndPermission(role.getId());

        HashMap<String,Object> map = new HashMap<>();
        map.put("roleId",role.getId());
        for (Integer menuId : menuIds) {
            map.put("menuId",menuId);
            roleDao.saveRoleAndMenu(map);
        }
        for (Integer permissionId : permissionIds) {
            map.put("permissionId",permissionId);
            roleDao.saveRoleAndPermission(map);
        }

        roleDao.updateRole(role);
    }

    @Override
    public List<Role> findAll() {

        return roleDao.findAll();
    }
}
