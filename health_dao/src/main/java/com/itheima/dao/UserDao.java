package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.HashMap;
import java.util.List;

public interface UserDao {

    @Select("select * from t_user where username = #{username}")
    User findUserUsername(String username);

    @Update("update t_user set password = #{password} where username = #{username}")
    void updatePassword(User user);

    void addUser(User user);

    @Insert("insert into t_user_role values(#{userId},#{roleId})")
    void saveUserAndRole(HashMap<String, Object> map);

    Page<User> findPage(String queryString);

    @Select("select count(1) from t_user_role where user_id=#{id}")
    int findUserAndRole(Integer id);

    @Delete("delete from t_user where id=#{id}")
    void deleteUser(Integer id);

    @Select("select * from t_user where id=#{id}")
    User findUser(Integer id);

    @Select("select role_id from t_user_role where user_id=#{id}")
    List<Integer> findRoleIds(Integer id);

    @Delete("delete from t_user_role where user_id=#{id}")
    void deleteUserAndRole(Integer id);

    @Update("update t_user set birthday=#{birthday},gender=#{gender},username=#{username},password=#{password},remark=#{remark},station=#{station},telephone=#{telephone} where id = #{id}")
    void updateUser(User user);
}
