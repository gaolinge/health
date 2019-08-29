package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Role;
import com.itheima.service.CheckGroupService;
import com.itheima.service.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Reference
    private RoleService roleService;

    @PreAuthorize("hasAuthority('ROLE_ADD')")
    @RequestMapping("/add")
    public Result add(@RequestBody Role role , @RequestParam Integer[] menuIds,@RequestParam Integer[] permissionIds){

        try {
            roleService.addRole(role,menuIds,permissionIds);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }

    }

    @PreAuthorize("hasAuthority('ROLE_QUERY')")
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {

        return roleService.findPage(queryPageBean);
    }

    @PreAuthorize("hasAuthority('ROLE_DELETE')")
    @RequestMapping("/deleteRole")
    public Result deleteRole(Integer id) {

        try {
            roleService.deleteRole(id);
            return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_EDIT')")
    @RequestMapping("/findRole")
    public Result findCheckGroup(Integer id) {

        Role role = roleService.findRole(id);

        return new Result(true,"",role);
    }

    @PreAuthorize("hasAuthority('ROLE_EDIT')")
    @RequestMapping("/findMenuIds")
    public List<Integer> findMenuIds(Integer id){

        return roleService.findMenuIds(id);

    }

    @PreAuthorize("hasAuthority('ROLE_EDIT')")
    @RequestMapping("/findPermissionIds")
    public List<Integer> findPermissionIds(Integer id){

        return roleService.findPermissionIds(id);

    }


    @PreAuthorize("hasAuthority('ROLE_EDIT')")
    @RequestMapping("/updateRole")
    public Result updateRole(@RequestBody Role role,@RequestParam Integer[] menuIds,@RequestParam Integer[] permissionIds) {

        try {
            roleService.updateRole(role,menuIds,permissionIds);
            return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }


    @PreAuthorize("hasAuthority('USER_ADD')")
    @RequestMapping("/findAll")
    public Result findAll(){

        List<Role> list = roleService.findAll();

        return new Result(true,"",list);
    }


}
