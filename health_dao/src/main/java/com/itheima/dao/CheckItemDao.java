package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface CheckItemDao {

    @Insert("insert into t_checkitem values(null,#{code},#{name},#{sex},#{age},#{price},#{type},#{attention},#{remark})")
    void saveCheckItem(CheckItem checkItem);

    Page<CheckItem> findCheckItemByKeyword(String queryString);

    @Select("select count(*) from t_checkgroup_checkitem where checkitem_id = #{id}")
    int findCheckItemCount(Integer id);

    @Delete("delete from t_checkitem where id = #{id}")
    void deleteCheckItemById(Integer id);

    @Select("select * from t_checkitem where id = #{id}")
    CheckItem findCheckItemById(Integer id);

    @Update("update t_checkitem set code = #{code},name = #{name},sex = #{sex},age = #{age},price = #{price},type = #{type},attention = #{attention},remark = #{remark} where id = #{id}")
    void updateCheckItemById(CheckItem checkItem);

    @Select("select * from t_checkitem")
    List<CheckItem> findAll();
}
