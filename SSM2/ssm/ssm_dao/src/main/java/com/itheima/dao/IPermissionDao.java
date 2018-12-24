package com.itheima.dao;

import com.itheima.Permission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPermissionDao {
    //根据角色id查询到权限
    @Select("select * from permission where id in (select permissionId from role_permission where roleId=#{id})")
    public List<Permission> findPermissionByRoleId(String id);

    //查询所有的权限
    @Select("select * from permission")
    List<Permission> findAll();

    //添加权限
    @Insert("insert into permission(permissionName,url) values(#{permissionName},#{url})")
    void save(Permission permission);

    //删除权限
    @Delete("delete from permission where id =#{pId}")
    void delete(String pId);
}
