package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.MemberDao;
import com.itheima.dao.MenuDao;
import com.itheima.dao.RoleDao;
import com.itheima.dao.UserDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Menu;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


@Transactional
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private MenuDao menuDao;

    @Override
    public User findUserUsername(String username) {
        return userDao.findUserUsername(username);
    }

    @Override
    public void updatePassword(User user) {
        userDao.updatePassword(user);
    }

    @Override
    public Set findUserByUsername(String username) {

        User user = userDao.findUserUsername(username);

        Set<Role> roles = roleDao.findRoleByUid(user.getId());

        HashMap<String,Object> map = new HashMap<>();

        LinkedHashSet<Menu> menus = null;

        for (Role role : roles) {

            map.put("rid",role.getId());

            menus = menuDao.findMenuByRid(role.getId());

            for (Menu menu : menus) {

                map.put("parentMenuId",menu.getId());

                List<Menu> children = menuDao.findChildrenByMid(map);

                menu.setChildren(children);
            }
        }

        return menus;
    }

    @Override
    public void addUser(User user, Integer[] roleIds) {

        //先保存用户,并获取用户id
        userDao.addUser(user);

        //保存中间表数据
        HashMap<String,Object> map = new HashMap<>();
        map.put("userId",user.getId());
        for (Integer roleId : roleIds) {
            map.put("roleId",roleId);
            userDao.saveUserAndRole(map);
        }
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        Page<User> page = userDao.findPage(queryPageBean.getQueryString());

        return new PageResult(page.getTotal(),page.getResult());

    }


    @Override
    public void deleteUser(Integer id) {
        //查询是否又关联
        int count = userDao.findUserAndRole(id);
        //是否删除
        if (count <= 0) {

            userDao.deleteUser(id);

        }else{

            throw new RuntimeException("该检查组被关联了...");
        }
    }

    @Override
    public User findUser(Integer id) {

        return userDao.findUser(id);
    }

    @Override
    public List<Integer> findRoleIds(Integer id) {
        return userDao.findRoleIds(id);
    }

    @Override
    public void updateUser(User user, Integer[] roleIds) {

        userDao.deleteUserAndRole(user.getId());

        HashMap<String,Object> map = new HashMap<>();
        map.put("userId",user.getId());
        for (Integer roleId : roleIds) {
            map.put("roleId",roleId);
            userDao.saveUserAndRole(map);
        }

        userDao.updateUser(user);
    }
}
