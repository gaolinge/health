package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.ReportDao;
import com.itheima.pojo.Setmeal;
import com.itheima.service.ReportService;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service(interfaceClass = ReportService.class)
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDao reportDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;

    @Override
    public Integer getMemberCount(String date) {
        date = date+"-31";
        return reportDao.getMemberCount(date);
    }

    @Override
    public List<Setmeal> getSetmealNames() {

        return reportDao.findAllSetmeal();
    }

    @Override
    public Integer findOrderMemberCount(Integer id) {
        return reportDao.findOrderMemberCount(id);
    }

    @Override
    public Map<String, Object> getBusinessReportData() throws Exception {

        /*
        reportData:{
                    reportDate:null, //日期
                    todayNewMember :0,//新增会员数
                    totalMember :0, //总会员数
                    thisWeekNewMember :0,//本周新增会员数
                    thisMonthNewMember :0,//本月新增会员数
                    todayOrderNumber :0, //今天预约数
                    todayVisitsNumber :0, //今天到诊数
                    thisWeekOrderNumber :0,
                    thisWeekVisitsNumber :0,
                    thisMonthOrderNumber :0,
                    thisMonthVisitsNumber :0,
                    hotSetmeal :[
                                    {name:'阳光爸妈升级肿瘤12项筛查（男女单人）体检套餐',setmeal_count:200,proportion:0.222},
                                    {name:'阳光爸妈升级肿瘤12项筛查体检套餐',setmeal_count:200,proportion:0.222}
                                 ]
        }*/

        HashMap<String,Object> reportData = new HashMap<>();

        //获取今天日期
        String today = DateUtils.parseDate2String(DateUtils.getToday());
        System.out.println(today);
        reportData.put("reportDate",today);

        //获得本周一的日期
        String thisWeekFirstDay = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());

        //获得本月第一天的日期
        String thisMondayFirstDay = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());


        //新增会员数
        Integer todayNewMember = memberDao.findTodayNewMemberCount(today);
        reportData.put("todayNewMember",todayNewMember);

        //总会员数
        Integer totalMember =memberDao.findTotalMember();
        reportData.put("totalMember",totalMember);

        //本周新增会员数
        Integer thisWeekNewMember = memberDao.findThisWeekNewMemberCount(thisWeekFirstDay);
        reportData.put("thisWeekNewMember",thisWeekNewMember);

        //本月新增会员数
        Integer thisMonthNewMember  = memberDao.findThisMonthNewMemberCount(thisMondayFirstDay);
        reportData.put("thisMonthNewMember",thisMonthNewMember);

        //今天预约数
        Integer todayOrderNumber = orderDao.todayOrderNumber(today);
        reportData.put("todayOrderNumber",todayOrderNumber);

        //今天到诊数
        Integer todayVisitsNumber = orderDao.todayViscitsNumber(today);
        reportData.put("todayVisitsNumber",todayVisitsNumber);

        //本周预约数
        Integer thisWeekOrderNumber = orderDao.thisWeekOrderNumber(thisWeekFirstDay);
        reportData.put("thisWeekOrderNumber",thisWeekOrderNumber);

        //本周到诊数
        Integer thisWeekVisitsNumber = orderDao.thisWeekVisitsNumber(thisWeekFirstDay);
        reportData.put("thisWeekVisitsNumber",thisWeekVisitsNumber);

        //本月预约数
        Integer thisMonthOrderNumber = orderDao.thisMonthOrderNumber(thisMondayFirstDay);
        reportData.put("thisMonthOrderNumber",thisMonthOrderNumber);

        //本月到诊数
        Integer thisMonthVisitsNumber = orderDao.thisMonthVisitsNumber(thisMondayFirstDay);
        reportData.put("thisMonthVisitsNumber",thisMonthVisitsNumber);

        List<Map<String,Object>> hotSetmeal = orderDao.findHotSetmeal();
        reportData.put("hotSetmeal",hotSetmeal);

        return reportData;
    }
}
