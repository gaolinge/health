package com.itheima.service;

import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface ReportService {
    Integer getMemberCount(String date);

    List<Setmeal> getSetmealNames();

    Integer findOrderMemberCount(Integer id);

    Map<String,Object> getBusinessReportData() throws Exception;

}
