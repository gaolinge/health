package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderSettingDao;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderService;
import com.itheima.utils.DateUtils;
import org.apache.commons.codec.language.bm.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Transactional
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Autowired
    private SetmealDao setmealDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private MemberDao memberDao;

    @Override
    public Result submit(Map map) throws Exception {

        //1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
        String orderDate = (String) map.get("orderDate");
        OrderSetting orderSetting = orderSettingDao.findOrderSettingByDate(orderDate);

        if (orderSetting == null) {

            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }

        //2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
        if (orderSetting.getNumber() == orderSetting.getReservations()) {

            return new Result(false,MessageConstant.ORDER_FULL);
        }

        //判断是否为会员
        String sex = (String) map.get("sex");
        String name = (String) map.get("name");
        String idCard = (String) map.get("idCard");
        String setmealId = (String) map.get("setmealId");
        String telephone = (String) map.get("telephone");
        Member member = memberDao.getMember(telephone);
        if (member == null) {
            //3、检查当前用户不是会员，自动完成注册并进行预约
            member = new Member();
            member.setSex(sex);
            member.setName(name);
            member.setIdCard(idCard);
            member.setRegTime(new Date());
            member.setPhoneNumber(telephone);

            memberDao.addMember(member);


        }else{
            //4、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约
            Order order = new Order();
            order.setMemberId(member.getId());
            order.setOrderDate(DateUtils.parseString2Date(orderDate));
            order.setSetmealId(Integer.parseInt(setmealId));
            order = orderDao.findOrder(order);

            if (order != null) {

                return new Result(false,MessageConstant.HAS_ORDERED);
            }


        }

        //添加预约
        Order order = new Order(member.getId(), DateUtils.parseString2Date(orderDate),Order.ORDERTYPE_WEIXIN,Order.ORDERSTATUS_NO,Integer.parseInt(setmealId));

        orderDao.addOrder(order);


        //5、预约成功，更新当日的已预约人数
        orderSetting.setReservations(orderSetting.getReservations()+1);
        orderSettingDao.updateReservations(orderSetting);


        return new Result(true,MessageConstant.ORDER_SUCCESS,order);
    }

    @Override
    public Map findById(Integer id) {
        return orderDao.findById(id);
    }
}
