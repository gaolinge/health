package com.itheima.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Menu;
import com.itheima.pojo.Permission;
import com.itheima.service.MenuService;
import com.itheima.service.PermissionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Reference
    private PermissionService permissionService;

    //@PreAuthorize("hasAuthority('MENU_ADD')")
    @RequestMapping("/addPermission")
    public Result addPermission(@RequestBody Permission permission) {

        try {

            permissionService.addPermission(permission);

            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }

    //@PreAuthorize("hasAuthority('MENU_QUERY')")
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {

        return permissionService.findPage(queryPageBean);
    }

    //@PreAuthorize("hasAuthority('MENU_DELETE')")
    @RequestMapping("/deletePermission")
    public Result deletePermission(Integer id) {

        try {

            permissionService.deletePermission(id);

            return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }

    //@PreAuthorize("hasAuthority('MENU_EDIT')")
    @RequestMapping("/findPermissionById")
    public Result findPermissionById(Integer id) {

        Result result = new Result();

        Permission permission = permissionService.findPermissionById(id);

        result.setData(permission);

        return result;
    }

    //@PreAuthorize("hasAuthority('MENU_EDIT')")
    @RequestMapping("/updatePermission")
    public Result updatePermission(@RequestBody Permission permission) {

        try {
            permissionService.updatePermission(permission);

            return new Result(true ,MessageConstant.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true ,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }


    @PreAuthorize("hasAuthority('ROLE_ADD')")
    @RequestMapping("/findAll")
    public Result findAll(){

        try {
            List<Permission> list = permissionService.findAll();
            return new Result(true,MessageConstant.ADD_SETMEAL_FAIL,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
    }
}
