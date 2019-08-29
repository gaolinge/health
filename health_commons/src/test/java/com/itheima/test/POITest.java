package com.itheima.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class POITest {

    /*
        读取Excel中内容
     */
    @Test
    public void test() throws IOException {

        //1.获取工作簿对象
        XSSFWorkbook workbook = new XSSFWorkbook("C:\\Users\\Administrator\\Desktop\\信息表.xlsx");

        //2.获取表对象
        XSSFSheet sheet = workbook.getSheetAt(0);

        //遍历每一行
        for (Row row : sheet) {

            //遍历每一列
            for (Cell cell : row) {

                //获取每一列的值
                String value = cell.getStringCellValue();

                System.out.print(value+"\t");
            }

            System.out.println();

        }

        //关闭
        workbook.close();
    }


    /*
        将信息写到Excel中
     */
    @Test
    public void test02() throws Exception {

        //创建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();

        //创建表
        XSSFSheet sheet = workbook.createSheet("商品表");

        //创建行
        XSSFRow row1 = sheet.createRow(0);

        //创建列
        row1.createCell(0).setCellValue("商品");
        row1.createCell(1).setCellValue("价格");
        row1.createCell(2).setCellValue("数量");

        XSSFRow row2 = sheet.createRow(1);
        row2.createCell(0).setCellValue("篮球");
        row2.createCell(1).setCellValue("100");
        row2.createCell(2).setCellValue("10");

        XSSFRow row3 = sheet.createRow(2);
        row3.createCell(0).setCellValue("蛋糕");
        row3.createCell(1).setCellValue("12");
        row3.createCell(2).setCellValue("30");

        //创建流
        FileOutputStream fos = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\商品表.xlsx");

        //将文件写出
        workbook.write(fos);

        fos.close();
        workbook.close();

    }

}
