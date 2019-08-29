package com.itheima.service;

import com.itheima.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {

    void upload(List<OrderSetting> orderSettingList);

    List<Map<String,Object>> getOrderSettingByMonth(String date);

    List<OrderSetting> getOrderSettingByMonth1(String date);

    void updateNumber(OrderSetting orderSetting);

    void ClearOrderSetting(String dateStr);
}
