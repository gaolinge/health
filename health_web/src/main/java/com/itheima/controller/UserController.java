package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Reference
    private UserService userService;

    @RequestMapping("/login")
    public Result login(){


        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            String username = user.getUsername();

            Set set = userService.findUserByUsername(username);

            HashMap<String,Object> map = new HashMap<>();

            map.put("username",username);
            map.put("set",set);

            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.GET_USERNAME_FAIL);

        }


    }

    @RequestMapping("/updatePassword")
    public Result updatePassword(String password){

        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            String username = user.getUsername();

            password = encoder.encode(password);

            com.itheima.pojo.User user1 = new com.itheima.pojo.User();

            user1.setUsername(username);
            user1.setPassword(password);

            userService.updatePassword(user1);

            return new Result(true, MessageConstant.UPDATE_PASSWORD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.UPDATE_PASSWORD_FAIL);

        }

    }

    @PreAuthorize("hasAuthority('USER_ADD')")
    @RequestMapping("/addUser")
    public Result  add(@RequestBody com.itheima.pojo.User user , @RequestParam Integer[] roleIds){

        try {
            user.setPassword( encoder.encode(user.getPassword()));
            userService.addUser(user,roleIds);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }

    }

    @PreAuthorize("hasAuthority('USER_QUERY')")
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {

        return userService.findPage(queryPageBean);
    }

    @PreAuthorize("hasAuthority('USER_DELETE')")
    @RequestMapping("/deleteUser")
    public Result deleteUser(Integer id) {

        try {
            userService.deleteUser(id);
            return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('USER_EDIT')")
    @RequestMapping("/findUser")
    public Result findUser(Integer id) {

        com.itheima.pojo.User user = userService.findUser(id);

        return new Result(true,"",user);
    }

    @PreAuthorize("hasAuthority('USER_EDIT')")
    @RequestMapping("/findRoleIds")
    public List<Integer> findRoleIds(Integer id){

        return userService.findRoleIds(id);

    }


    @PreAuthorize("hasAuthority('USER_EDIT')")
    @RequestMapping("/updateUser")
    public Result updateUser(@RequestBody com.itheima.pojo.User user, @RequestParam Integer[] roleIds) {

        try {
            if (user.getPassword().length() < 30) {

                user.setPassword( encoder.encode(user.getPassword()));

            }
            userService.updateUser(user,roleIds);
            return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }


}
