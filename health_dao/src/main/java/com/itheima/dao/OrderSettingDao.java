package com.itheima.dao;

import com.itheima.pojo.OrderSetting;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

public interface OrderSettingDao {

    @Select("select count(*) from t_ordersetting where orderDate = #{orderDate}")
    int findOrderSetting(Date orderDate);

    @Insert("insert into t_ordersetting (orderDate,number) values (#{orderDate},#{number})")
    void addOrderSetting(OrderSetting orderSetting);

    @Update("update t_ordersetting set number = #{number} where orderDate = #{orderDate}")
    void updateOrderSetting(OrderSetting orderSetting);

    @Select("select * from t_ordersetting where orderDate like '${value}%'")
    List<OrderSetting> getOrderSettingByMonth(String date);

    @Select("select * from t_ordersetting where orderDate = #{orderDate}")
    OrderSetting findOrderSettingByDate(String orderDate);

    @Update("update t_ordersetting set reservations = #{reservations} where id = #{id}")
    void updateReservations(OrderSetting orderSetting);

    @Delete("delete from t_ordersetting where orderDate<=#{dateStr}")
    void ClearOrderSetting(String dateStr);
}
