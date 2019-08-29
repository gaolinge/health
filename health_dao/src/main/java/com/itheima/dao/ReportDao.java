package com.itheima.dao;

import com.itheima.pojo.Setmeal;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ReportDao {

    @Select("select count(*) from t_member where regTime<=#{date}")
    Integer getMemberCount(String date);

    @Select("select * from t_setmeal")
    List<Setmeal> findAllSetmeal();

    @Select("select count(*) from t_member m , t_order o where m.id=o.member_id and o.setmeal_id=#{id}")
    Integer findOrderMemberCount(Integer id);
}
