package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.utils.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;

@Transactional
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    @Autowired
    private JedisPool jedisPool;

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        Page<Setmeal> page = setmealDao.findSetmealByKeyword(queryPageBean.getQueryString());

        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public Setmeal findSetmeal(Integer id) {

        Setmeal setmeal = setmealDao.findSetmeal(id);

        //编辑时要删除Redis数据库中数据
        jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());

        return setmeal;
    }

    @Override
    public List<Integer> findCheckGroupIds(Integer id) {
        return setmealDao.findCheckGroupIds(id);
    }

    @Override
    public void deletSetmeal(Integer id) {

        //先删除Redis数据库
        findSetmeal(id);

        //查询是否被关联
        int count = setmealDao.findSetmealAndCheckGroup(id);


        if (count <= 0) {

            setmealDao.deletSetmeal(id);
        }else {

            throw new RuntimeException("该套餐被关联,不能删除");
        }
    }

    @Override
    public void addSetmeal(Setmeal setmeal, Integer[] checkgroupIds) {

        //保存Setmeal
        setmealDao.addSetmeal(setmeal);

        //添加时,将图片名保存到Redis数据库
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());

        //中间表填充数据
        HashMap<String,Object> map = new HashMap<>();
        map.put("setmeal_id",setmeal.getId());

        for (Integer checkgroupId : checkgroupIds) {

            map.put("checkGroup_id",checkgroupId);

            setmealDao.addSetmealAndCheckGroup(map);
        }
    }

    @Override
    public void updateSetmeal(Setmeal setmeal, Integer[] checkgroupIds) {

        System.out.println(setmeal);
        //修改套餐数据
        setmealDao.updateSetmeal(setmeal);

        //修改完要重新保存Redis数据库中图片
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());


        //删除中间表
        setmealDao.deleteSetmealAndCheckGroup(setmeal.getId());

        //中间表填充数据
        HashMap<String,Object> map = new HashMap<>();
        map.put("setmeal_id",setmeal.getId());

        for (Integer checkgroupId : checkgroupIds) {

            map.put("checkGroup_id",checkgroupId);

            setmealDao.addSetmealAndCheckGroup(map);
        }

    }

    @Override
    public List<Setmeal> getSetmeal() {

        String getSetmeal = jedisPool.getResource().get("getSetmeal");

        if (getSetmeal == null) {

            List<Setmeal> setmeals = setmealDao.findAll();

            getSetmeal = JSON.toJSONString(setmeals);

            jedisPool.getResource().set("getSetmeal",getSetmeal);

        }

        List<Setmeal> list =  JSON.parseObject(getSetmeal, new TypeReference<List<Setmeal>>(){});


        return list;

    }

    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }
}
