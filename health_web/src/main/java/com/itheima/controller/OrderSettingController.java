package com.itheima.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.POIUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile) throws IOException {

        //使用解析工具
        List<String[]> list = POIUtils.readExcel(excelFile);

        //将List<String[]>转换为List<OrderSetting>
        List<OrderSetting> orderSettingList=new ArrayList<>();
        for (String[] str : list) {

            OrderSetting os = new OrderSetting(new Date(str[0]), Integer.parseInt(str[1]));

            orderSettingList.add(os);

        }

        try {
            orderSettingService.upload(orderSettingList);
            return new Result(true, MessageConstant.UPLOAD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.UPLOAD_FAIL);
        }


    }

    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date) {

        try {
            //List<Map<String,Object>> list = orderSettingService.getOrderSettingByMonth(date);
            List<OrderSetting> list = orderSettingService.getOrderSettingByMonth1(date);
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }

    }

    @PreAuthorize("hasAuthority('ORDERSETTING')")
    @RequestMapping("/updateNumber")
    public Result updateNumber(String orderDate,int number) throws ParseException {

        OrderSetting orderSetting = new OrderSetting();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date date = format.parse(orderDate);

        orderSetting.setOrderDate(date);

        orderSetting.setNumber(number);
        try {
            orderSettingService.updateNumber(orderSetting);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }
}
