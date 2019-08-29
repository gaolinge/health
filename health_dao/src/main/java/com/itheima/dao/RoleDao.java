package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface RoleDao {

    @Select("select * from t_user_role ur,t_role r where ur.role_id=r.id and ur.user_id=#{id}")
    Set<Role> findRoleByUid(Integer id);

    void saveRole(Role role);

    @Insert("insert into t_role_menu values(#{roleId},#{menuId})")
    void saveRoleAndMenu(HashMap<String, Object> map);

    @Insert("insert into t_role_permission values(#{roleId},#{permissionId})")
    void saveRoleAndPermission(HashMap<String, Object> map);

    Page<Role> findPage(String queryString);

    @Select("select count(1) from t_role_menu where role_id=#{id}")
    int findRoleAndMenu(Integer id);

    @Select("select count(1) from t_role_permission where role_id=#{id}")
    int findRolePermission(Integer id);

    @Delete("delete from t_role where id = #{id}")
    void deleteRole(Integer id);

    @Select("select * from t_role where id = #{id}")
    Role findRole(Integer id);

    @Select("select menu_id from t_role_menu where role_id=#{id}")
    List<Integer> findMenuIds(Integer id);

    @Select("select permission_id from t_role_permission where role_id=#{id}")
    List<Integer> findPermissionIds(Integer id);

    @Delete("delete from t_role_menu where role_id = #{id}")
    void deleteRoleAndMenu(Integer id);

    @Delete("delete from t_role_permission where role_id = #{id}")
    void deleteRoleAndPermission(Integer id);

    @Update("update t_role set name=#{name},keyword=#{keyword},description=#{description} where id=#{id} ")
    void updateRole(Role role);

    @Select("select * from t_role")
    List<Role> findAll();
}
