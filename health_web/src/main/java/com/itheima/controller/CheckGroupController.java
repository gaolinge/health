package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckGroupService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkGroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    @PreAuthorize("hasAuthority('CHECKGROUP_ADD')")
    @RequestMapping("/add")
    public Result  add(@RequestBody CheckGroup checkGroup , @RequestParam Integer[] checkitemIds){

        try {
            checkGroupService.addCheckGroup(checkGroup,checkitemIds);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }

    }

    @PreAuthorize("hasAuthority('CHECKGROUP_QUERY')")
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {

        return checkGroupService.findPage(queryPageBean);
    }

    @PreAuthorize("hasAuthority('CHECKGROUP_DELETE')")
    @RequestMapping("/delete")
    public Result deleteCheckGroup(Integer id) {

        try {
            checkGroupService.deleteCheckGroup(id);
            return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('CHECKGROUP_EDIT')")
    @RequestMapping("/findCheckGroup")
    public Result findCheckGroup(Integer id) {

        CheckGroup checkGroup = checkGroupService.findCheckGroup(id);

        return new Result(true,"",checkGroup);
    }

    @PreAuthorize("hasAuthority('CHECKGROUP_EDIT')")
    @RequestMapping("/findCheckItemIds")
    public List<Integer> findCheckItemIds(Integer id){

        return checkGroupService.findCheckItemIds(id);

    }


    @PreAuthorize("hasAuthority('CHECKGROUP_EDIT')")
    @RequestMapping("/updateCheckGroup")
    public Result updateCheckGroup(@RequestBody CheckGroup checkGroup,@RequestParam Integer[] checkitemIds) {

        try {
            checkGroupService.updateCheckGroup(checkGroup,checkitemIds);
            return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }


    @PreAuthorize("hasAuthority('SETMEAL_EDIT')")
    @RequestMapping("/findAll")
    public Result findAll(){

        List<CheckGroup> list = checkGroupService.findAll();

        return new Result(true,"",list);
    }


}
