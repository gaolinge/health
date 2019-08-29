package com.itheima.dao;

import com.itheima.pojo.Order;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface OrderDao {

    @Select("select * from t_order where member_id=#{memberId} and setmeal_id=#{setmealId} and orderDate=#{orderDate}")
    Order findOrder(Order order);

    void addOrder(Order order);

    @Select("select m.name member,s.name setmeal ,o.orderDate orderDate,o.orderType orderType from t_member m,t_order o,t_setmeal s where o.member_id=m.id and o.setmeal_id=s.id and o.id = #{id}")
    Map findById(Integer id);

    @Select("select count(*) from t_order where orderDate=#{today}")
    Integer todayOrderNumber(String today);

    @Select("select count(*) from t_order where orderDate=#{today} and orderStatus='已到诊'")
    Integer todayViscitsNumber(String today);

    @Select("select count(*) from t_order where orderDate>=#{thisWeekFirstDay}")
    Integer thisWeekOrderNumber(String thisWeekFirstDay);

    @Select("select count(*) from t_order where orderDate>=#{thisWeekFirstDay} and orderStatus='已到诊'")
    Integer thisWeekVisitsNumber(String thisWeekFirstDay);

    @Select("select count(*) from t_order where orderDate>=#{thisMondayFirstDay}")
    Integer thisMonthOrderNumber(String thisMondayFirstDay);

    @Select("select count(*) from t_order where orderDate>=#{thisMondayFirstDay} and orderStatus='已到诊'")
    Integer thisMonthVisitsNumber(String thisMondayFirstDay);

    @Select("SELECT s.name, COUNT(o.id) setmeal_count, COUNT(o.id)/(SELECT COUNT(id) FROM t_order ) proportion ,remark\n" +
            "FROM t_setmeal s, t_order o \n" +
            "WHERE s.id = o.setmeal_id\n" +
            "GROUP BY s.name ORDER BY  setmeal_count DESC LIMIT 0,4")
    List<Map<String,Object>> findHotSetmeal();
}
