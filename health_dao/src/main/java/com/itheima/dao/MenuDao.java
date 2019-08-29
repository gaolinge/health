package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Menu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

public interface MenuDao {

    @Select("select * from t_menu m,t_role_menu rm where m.id=rm.menu_id and m.level=1 and rm.role_id = #{id}")
    LinkedHashSet<Menu> findMenuByRid(Integer id);

    @Select("select * from t_menu m,t_role_menu rm where m.id=rm.menu_id and m.parentMenuId =#{parentMenuId} and role_id =#{rid}")
    List<Menu> findChildrenByMid(HashMap<String, Object> map);

    @Insert("insert into t_menu values(null,#{name},#{linkUrl},#{path},#{priority},#{icon},#{description},#{parentMenuId},#{level})")
    void addMenu(Menu menu);

    Page<Menu> findPage(String queryString);

    @Select("select count(1) from t_role_menu where menu_id = #{id}")
    int findMenuAndRole(Integer id);

    @Delete("delete from t_menu where id=#{id}")
    void deleteMenu(Integer id);

    @Select("select * from t_menu where id=#{id}")
    Menu findMenuById(Integer id);

    @Update("update t_menu set name=#{name},linkUrl=#{linkUrl},path=#{path},priority=#{priority},icon=#{icon},description=#{description},parentMenuId=#{parentMenuId} where level=#{level}")
    void updateMenu(Menu menu);

    @Select("select * from t_menu")
    List<Menu> findAll();
}
