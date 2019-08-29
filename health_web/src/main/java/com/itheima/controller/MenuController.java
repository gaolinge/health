package com.itheima.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Menu;
import com.itheima.service.MenuService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Reference
    private MenuService menuService;

    @PreAuthorize("hasAuthority('MENU_ADD')")
    @RequestMapping("/addMenu")
    public Result addMenu(@RequestBody Menu menu) {

        try {

            menuService.addMenu(menu);

            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('MENU_QUERY')")
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {

        return menuService.findPage(queryPageBean);
    }

    @PreAuthorize("hasAuthority('MENU_DELETE')")
    @RequestMapping("/deleteMenu")
    public Result deleteMenu(Integer id) {

        try {

            menuService.deleteMenu(id);

            return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('MENU_EDIT')")
    @RequestMapping("/findMenuById")
    public Result findMenuById(Integer id) {

        Result result = new Result();

        Menu menu = menuService.findMenuById(id);

        result.setData(menu);

        return result;
    }

    @PreAuthorize("hasAuthority('MENU_EDIT')")
    @RequestMapping("/updateMenu")
    public Result updateMenu(@RequestBody Menu menu) {

        try {
            menuService.updateMenu(menu);

            return new Result(true ,MessageConstant.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true ,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }


    @PreAuthorize("hasAuthority('MENU_ADD')")
    @RequestMapping("/findAll")
    public Result findAll(){

        try {
            List<Menu> list = menuService.findAll();
            return new Result(true,MessageConstant.ADD_SETMEAL_FAIL,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
    }
}
