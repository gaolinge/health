package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Permission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Set;

public interface PermissionDao {

    @Select("select * from t_role_permission rp ,t_permission p where rp.permission_id = p.id and rp.role_id=#{id}")
    Set<Permission> findPermissionByRid(Integer id);

    @Insert("insert into t_permission values(null,#{name},#{keyword},#{description})")
    void addPermission(Permission permission);

    Page<Permission> findPage(String queryString);

    @Select("select count(1) from t_role_permission where permission_id = #{id}")
    int findPermissionAndRole(Integer id);

    @Delete("delete from t_permission where id=#{id}")
    void deletePermission(Integer id);

    @Select("select * from t_permission where id=#{id}")
    Permission findPermissionById(Integer id);

    @Update("update t_permission set name=#{name},keyword=#{keyword},description=#{description} where id=#{id}")
    void updatePermission(Permission permission);

    @Select("select * from t_permission")
    List<Permission> findAll();

}
