package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Setmeal;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.HashMap;
import java.util.List;

public interface SetmealDao {

    Page<Setmeal> findSetmealByKeyword(String queryString);

    @Select("select * from t_setmeal where id = #{id}")
    Setmeal findSetmeal(Integer id);

    @Select("select checkgroup_id from t_setmeal_checkgroup where setmeal_id = #{id}")
    List<Integer> findCheckGroupIds(Integer id);

    @Select("select count(*) from t_setmeal_checkgroup where setmeal_id = #{id} ")
    int findSetmealAndCheckGroup(Integer id);

    @Delete("delete from t_setmeal where id = #{id}")
    void deletSetmeal(Integer id);

    void addSetmeal(Setmeal setmeal);

    @Insert("insert into t_setmeal_checkgroup values(#{setmeal_id},#{checkGroup_id})")
    void addSetmealAndCheckGroup(HashMap<String, Object> map);

    @Update("update t_setmeal set name = #{name},code = #{code},helpCode = #{helpCode},sex = #{sex},age = #{age},price = #{price},remark = #{remark},attention = #{attention},img = #{img} where id = #{id}")
    void updateSetmeal(Setmeal setmeal);

    @Delete("delete from t_setmeal_checkgroup where setmeal_id = #{id}")
    void deleteSetmealAndCheckGroup(Integer id);

    @Select("select * from t_setmeal")
    List<Setmeal> findAll();

    Setmeal findById(Integer id);
}
