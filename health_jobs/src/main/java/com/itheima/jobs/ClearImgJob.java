package com.itheima.jobs;

import com.itheima.utils.QiniuUtils;
import com.itheima.utils.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class ClearImgJob {

    @Autowired
    private JedisPool jedisPool;

    public void clearImg() {

        //获取差值
        Set<String> sdiff = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);

        //遍历差值
        for (String fileName : sdiff) {

            //删除七牛云中图片
            QiniuUtils.deleteFileFromQiniu(fileName);

            //删除Redis中图片
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);


        }
    }
}
