package com.itheima.web.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Reference
    private MemberService memberService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/login")
    public Result login(@RequestBody Map map,HttpServletRequest request){

        try {
            String telephone = (String) map.get("telephone");
            String validateCode = (String) map.get("validateCode");

            String r_ValidateCode = jedisPool.getResource().get(RedisMessageConstant.SENDTYPE_LOGIN + "_" + telephone);

            //过期或不存在
            /*if (r_ValidateCode == null || !r_ValidateCode.equals(validateCode)) {

                return new Result(false, MessageConstant.VALIDATECODE_ERROR);
            }*/

            //判断是否是会员
            Member member = memberService.findMemberByTelephone(telephone);

            if (member == null) {

                member = new Member();

                member.setRegTime(new Date());

                member.setPhoneNumber(telephone);

                memberService.addMember(member);
            }

            request.getSession().setAttribute("member",member);

            return new Result(true,MessageConstant.LOGIN_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.LOGIN_FAIL);
        }

    }
}
