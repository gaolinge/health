package com.itheima.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import com.sun.xml.internal.rngom.ast.util.CheckingSchemaBuilder;
import org.apache.ibatis.annotations.Insert;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkItem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")
    @RequestMapping("/addCheckItem")
    public Result addCheckItem(@RequestBody CheckItem checkItem) {

        try {

            checkItemService.addCheckItem(checkItem);

            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {

        return checkItemService.findPage(queryPageBean);
    }

    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")
    @RequestMapping("/deleteCheckItem")
    public Result deleteCheckItem(Integer id) {

        try {

            checkItemService.deleteCheckItem(id);

            return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('CHECKITEM_EDIT')")
    @RequestMapping("/findCheckItemById")
    public Result findCheckItemById(Integer id) {

        Result result = new Result();

        CheckItem checkItem = checkItemService.findCheckItemById(id);

        result.setData(checkItem);

        return result;
    }

    @PreAuthorize("hasAuthority('CHECKITEM_EDIT')")
    @RequestMapping("/updateCheckItem")
    public Result updateCheckItem(@RequestBody CheckItem checkItem) {

        try {
            checkItemService.updateCheckItemById(checkItem);

            return new Result(true ,MessageConstant.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true ,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }


    @PreAuthorize("hasAuthority('CHECKGROUP_EDIT')")
    @RequestMapping("/findAll")
    public Result findAll(){

        try {
            List<CheckItem> list = checkItemService.findAll();
            return new Result(true,MessageConstant.ADD_SETMEAL_FAIL,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
    }
}
