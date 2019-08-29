package com.itheima.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.ReportService;
import com.itheima.utils.DateUtils;
import javafx.scene.AmbientLight;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private ReportService reportService;

    @RequestMapping("/getMemberReport")
    public Result getMemberReport(@RequestParam Map dateMap) throws Exception {

        HashMap<String,Object> map = new HashMap<>();

        ArrayList<String> months = new ArrayList<>();
        ArrayList<Integer> memberCount = new ArrayList<>();

        if (dateMap.size() <= 0) {

            Calendar calendar = Calendar.getInstance();

            calendar.add(calendar.MONTH,-12);

            for (int i = 0; i < 12; i++) {

                calendar.add(calendar.MONTH,1);

                String date = DateUtils.parseDate2String(calendar.getTime(), "yyyy-MM");

                Integer count = reportService.getMemberCount(date);

                memberCount.add(count);

                months.add(date);

            }

        }else{

            //初始时间
            Date dateStart = DateUtils.parseString2Date((String)dateMap.get("dateStart"));

            Calendar calStart = Calendar.getInstance();

            calStart.setTime(dateStart);

            //结束时间
            Date dateEnd = DateUtils.parseString2Date((String)dateMap.get("dateEnd"));

            Calendar calEnd = Calendar.getInstance();

            calEnd.setTime(dateEnd);

            //calEnd.add(calEnd.MONTH,1);


            while (true) {

                String dateStartStr = DateUtils.parseDate2String(calStart.getTime(), "yyyy-MM");

                Integer count = reportService.getMemberCount(dateStartStr);

                calStart.add(calStart.MONTH,1);

                memberCount.add(count);

                months.add(dateStartStr);

                if (calStart.getTime().getTime() > calEnd.getTime().getTime()) {

                    break;
                }

            }




        }



        map.put("months",months);
        map.put("memberCount",memberCount);


        return new Result(true,"",map);

    }

    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport(){

        HashMap<String,Object> map = new HashMap<>();
        ArrayList<String> setmealNames = new ArrayList<>();
        ArrayList<Map<String,Object>> setmealCount = new ArrayList<>();

        List<Setmeal> setmealList = reportService.getSetmealNames();

        for (Setmeal setmeal : setmealList) {

            setmealNames.add(setmeal.getName());

            Integer members = reportService.findOrderMemberCount(setmeal.getId());

            HashMap<String,Object> map1 = new HashMap<>();
            map1.put("value",members);
            map1.put("name",setmeal.getName());
            setmealCount.add(map1);

        }

        map.put("setmealNames",setmealNames);
        map.put("setmealCount",setmealCount);

        return new Result(true,"",map);
    }


    @RequestMapping("/getBusinessReportData")
    public Map<String,Object> getBusinessReportData() throws Exception {

        Map<String,Object> map = reportService.getBusinessReportData();

        return map;
    }

    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response) throws Exception {

        try {
            String path = request.getSession().getServletContext().getRealPath("template/report_template.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(path);

            XSSFSheet sheet = workbook.getSheetAt(0);


                /*reportDate:null, //日期
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
                            {name:'阳光爸妈升级肿瘤12项筛查（男女单人）体检套餐',setmeal_count:200,proportion:0.222,remark:''},
                            {name:'阳光爸妈升级肿瘤12项筛查体检套餐',setmeal_count:200,proportion:0.222,remark:''}
                            ]*/
            Map<String,Object> map = reportService.getBusinessReportData();

            String reportDate =(String) map.get("reportDate");
            Integer todayNewMember = (Integer) map.get("todayNewMember");
            Integer totalMember = (Integer) map.get("totalMember");
            Integer thisWeekNewMember = (Integer) map.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) map.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) map.get("todayOrderNumber");
            Integer todayVisitsNumber = (Integer) map.get("todayVisitsNumber");
            Integer thisWeekOrderNumber = (Integer) map.get("thisWeekOrderNumber");
            Integer thisWeekVisitsNumber = (Integer) map.get("thisWeekVisitsNumber");
            Integer thisMonthOrderNumber = (Integer) map.get("thisMonthOrderNumber");
            Integer thisMonthVisitsNumber = (Integer) map.get("thisMonthVisitsNumber");

            sheet.getRow(2).getCell(5).setCellValue(reportDate);
            sheet.getRow(4).getCell(5).setCellValue(todayNewMember);
            sheet.getRow(4).getCell(7).setCellValue(totalMember);
            sheet.getRow(5).getCell(5).setCellValue(thisWeekNewMember);
            sheet.getRow(5).getCell(7).setCellValue(thisMonthNewMember);
            sheet.getRow(7).getCell(5).setCellValue(todayOrderNumber);
            sheet.getRow(7).getCell(7).setCellValue(todayVisitsNumber);
            sheet.getRow(8).getCell(5).setCellValue(thisWeekOrderNumber);
            sheet.getRow(8).getCell(7).setCellValue(thisWeekVisitsNumber);
            sheet.getRow(9).getCell(5).setCellValue(thisMonthOrderNumber);
            sheet.getRow(9).getCell(7).setCellValue(thisMonthVisitsNumber);

            List<Map> list = (List<Map>) map.get("hotSetmeal");

            for (int i=0;i<list.size();i++) {

                String name = (String) list.get(i).get("name");
                Long setmeal_count = (Long)list.get(i).get("setmeal_count");
                BigDecimal proportion = (BigDecimal) list.get(i).get("proportion");


                sheet.getRow(12+i).getCell(4).setCellValue(name);
                sheet.getRow(12+i).getCell(5).setCellValue(setmeal_count);
                sheet.getRow(12+i).getCell(6).setCellValue(proportion.doubleValue());


            }

            //通过输出流进行文件下载
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
            workbook.write(out);

            out.flush();
            out.close();
            workbook.close();

            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }

    }
}
