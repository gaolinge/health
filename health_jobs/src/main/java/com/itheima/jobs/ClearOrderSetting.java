package com.itheima.jobs;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.DateUtils;

import java.util.Date;

public class ClearOrderSetting {

    @Reference
    private OrderSettingService orderSettingService;

    public void clearOrderSetting() throws Exception {

        Date date = new Date();

        String dateStr = DateUtils.parseDate2String(date, "yyyy-MM-dd");

        orderSettingService.ClearOrderSetting(dateStr);

    }
}
