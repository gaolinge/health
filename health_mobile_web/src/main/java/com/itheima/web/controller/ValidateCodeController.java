package com.itheima.web.controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.constant.ValidateCodeUtils;
import com.itheima.entity.Result;
import com.itheima.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/send4Order")
    public Result send4Order(String telephone) throws ClientException {

        try {
            //生成验证码
            String param = ValidateCodeUtils.generateValidateCode(6).toString();

            //发送验证码
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,param);
            //在Redis中存储一份
            jedisPool.getResource().setex(RedisMessageConstant.SENDTYPE_ORDER+"_"+telephone,60*5,param);

            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.SEND_VALIDATECODE_FAIL);
        }

    }

    @RequestMapping("/send4Login")
    public Result send4Login(String telephone) throws ClientException {

        try {
            //生成验证码
            Integer param = ValidateCodeUtils.generateValidateCode(6);

            //发送验证码
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,param.toString());

            //存一份Redis数据库中
            jedisPool.getResource().setex(RedisMessageConstant.SENDTYPE_LOGIN+"_"+telephone,100*60,param.toString());

            return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }


    }
}
