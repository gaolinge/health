package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService{

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void upload(List<OrderSetting> orderSettingList) {
        //遍历集合
        for (OrderSetting orderSetting : orderSettingList) {

            //判断时间是否为以前时间
            if (orderSetting.getOrderDate().getTime()>=new Date().getTime()) {

                int count = orderSettingDao.findOrderSetting(orderSetting.getOrderDate());

                if (count <= 0) {
                    //不存在是则添加数据
                    orderSettingDao.addOrderSetting(orderSetting);
                }else{
                    //存在时则修改数据
                    orderSettingDao.updateOrderSetting(orderSetting);
                }
            }

        }
    }

    @Override
    public List<Map<String, Object>> getOrderSettingByMonth(String date) {

        List<OrderSetting> listOrderSetting = orderSettingDao.getOrderSettingByMonth(date);

        List<Map<String, Object>> list = new ArrayList<>();

        for (OrderSetting orderSetting : listOrderSetting) {

            HashMap<String,Object> map = new HashMap<>();

            map.put("date",orderSetting.getOrderDate().getDate());
            map.put("number",orderSetting.getNumber());
            map.put("reservations",orderSetting.getReservations());

            list.add(map);
        }

        return list;
    }

    @Override
    public List<OrderSetting> getOrderSettingByMonth1(String date) {
        return orderSettingDao.getOrderSettingByMonth(date);
    }

    @Override
    public void updateNumber(OrderSetting orderSetting) {

        int count = orderSettingDao.findOrderSetting(orderSetting.getOrderDate());

        if (count <= 0) {
            //不存在是则添加数据
            orderSettingDao.addOrderSetting(orderSetting);
        }else{
            //存在时则修改数据
            orderSettingDao.updateOrderSetting(orderSetting);
        }
    }

    @Override
    public void ClearOrderSetting(String dateStr) {

        orderSettingDao.ClearOrderSetting(dateStr);
    }
}
