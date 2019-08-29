package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.PermissionService;
import com.itheima.service.RoleService;
import com.itheima.service.UserService;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;



public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    private UserService userService;

    @Reference
    private RoleService roleService;

    @Reference
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("username="+username);

        //查询数据库获取用户信息
        User user = userService.findUserUsername(username);

        if (user == null) {
            return null;
        }

        //根据用户id查询角色
        Set<Role> roles = roleService.findRoleByUid(user.getId());

        List<GrantedAuthority> list = new ArrayList<>();

        if (roles != null && roles.size() > 0) {

            //根据角色id获取权限
            for (Role role : roles) {

                Set<Permission> permissions = permissionService.findPermissionByRid(role.getId());

                if (permissions != null && permissions.size() > 0) {

                    for (Permission permission : permissions) {

                        list.add(new SimpleGrantedAuthority(permission.getKeyword()));
                    }
                }
            }
        }


        return new org.springframework.security.core.userdetails.User(username,user.getPassword(),list);
    }
}
