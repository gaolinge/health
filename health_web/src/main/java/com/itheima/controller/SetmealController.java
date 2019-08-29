package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.utils.QiniuUtils;
import com.itheima.utils.RedisConstant;
import com.itheima.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/upload")
    public Result upload(MultipartFile imgFile) {
        try {

            //获取文件名
            String filename = imgFile.getOriginalFilename();

            //生成唯一字符串
            filename = UploadUtils.getUUIDName(filename);

            //上传文件
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),filename);

            //使用redis数据库保存图片名
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,filename);

            return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,filename);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('SETMEAL_ADD')")
    @RequestMapping("/addSetmeal")
    public Result addSetmeal(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){

        try {
            setmealService.addSetmeal(setmeal,checkgroupIds);
            return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.ADD_SETMEAL_FAIL);
        }

    }

    @PreAuthorize("hasAuthority('SETMEAL_QUERY')")
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {

        return setmealService.findPage(queryPageBean);
    }

    @PreAuthorize("hasAuthority('SETMEAL_EDIT')")
    @RequestMapping("/findSetmeal")
    public Result findSetmeal(Integer id) {

        Setmeal setmeal = setmealService.findSetmeal(id);

        return new Result(true,"",setmeal);
    }

    @PreAuthorize("hasAuthority('SETMEAL_EDIT')")
    @RequestMapping("/findCheckGroupIds")
    public List<Integer> findCheckGroupIds(Integer id){

        return setmealService.findCheckGroupIds(id);

    }

    @PreAuthorize("hasAuthority('SETMEAL_EDIT')")
    @RequestMapping("/updateSetmeal")
    public Result updateSetmeal(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){

        try {
            setmealService.updateSetmeal(setmeal,checkgroupIds);
            return new Result(true,MessageConstant.EDIT_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.EDIT_SETMEAL_FAIL);
        }

    }

    @PreAuthorize("hasAuthority('SETMEAL_DELETE')")
    @RequestMapping("/deletSetmeal")
    public Result deletSetmeal(Integer id){

        try {
            setmealService.deletSetmeal(id);
            return new Result(true,"删除套餐成功");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除套餐失败");
        }

    }

}
