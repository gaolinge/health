package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.HashMap;
import java.util.List;

public interface CheckGroupDao {

    void saveCheckGroup(CheckGroup checkGroup);

    @Insert("insert into t_checkgroup_checkitem values(#{checkGroupId},#{checkitemId})")
    void saveCheckGroupAndCheckItem(HashMap<String, Object> map);

    Page<CheckGroup> findCheckGroupByKeyword(String queryString);

    @Select("select count(*) from t_checkgroup_checkitem where checkgroup_id = #{id}")
    int findCheckGroupAndItem(Integer id);

    @Delete("delete from t_checkgroup where id = #{id}")
    void deleteCheckGroup(Integer id);

    @Select("select * from t_checkgroup where id = #{id}")
    CheckGroup findCheckGroup(Integer id);

    @Select("select checkitem_id from t_checkgroup_checkitem gi where gi.checkgroup_id = #{id}")
    List<Integer> findCheckItemIds(Integer id);

    @Delete("delete from t_checkgroup_checkitem where checkgroup_id=#{id}")
    void deleteCheckGroupAndItem(Integer id);

    @Update("update t_checkgroup set code = #{code},name = #{name},helpCode = #{helpCode},sex = #{sex},remark = #{remark},attention = #{attention} where id = #{id}")
    void updateCheckGroup(CheckGroup checkGroup);

    @Select("select * from t_checkgroup")
    List<CheckGroup> findAll();
}
