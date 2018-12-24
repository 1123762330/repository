package com.itheima.dao;

import com.itheima.Permission;
import com.itheima.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoleDao {
    //根据用户id查询出所有对应的角色
    @Select("select * from role where id in (select roleId from users_role where userId=#{userId})")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "roleName", column = "roleName"),
            @Result(property = "roleDesc", column = "roleDesc"),
            @Result(property = "permissions",column = "id",javaType = java.util.List.class,many = @Many(select = "com.itheima.dao.IPermissionDao.findPermissionByRoleId"))
    })
    public List<Role> findRoleByUserId(String userId);

    //查询所有的角色
    @Select("select * from role")
    List<Role> findAll();

    //添加角色
    @Insert("insert into role(roleName,roleDesc) values(#{roleName},#{roleDesc})")
    void save(Role role);

    //根据roleId查询role
    @Select("select * from role where id=#{roleId}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "roleName",column = "roleName"),
            @Result(property = "roleDesc",column = "roleDesc"),
            @Result(property = "permissions",column = "id",javaType = java.util.List.class,many = @Many(select = "com.itheima.dao.IPermissionDao.findPermissionByRoleId"))
    })
    Role findById(String roleId);

    //根据roleid查询可以添加的权限
    @Select("select * from permission where id not in (select permissionId from role_permission where roleId=#{roleId})")
    List<Permission> findOtherRoles(String roleId);

    //给角色添加权限
    @Insert("insert into role_permission(roleId,permissionId) values(#{roleId},#{permissionId})")
    void addPermissionToRole(@Param("roleId") String roleId, @Param("permissionId") String permissionId);

    //查询角色下的相关权限
    @Select("select * from permission where id in (select permissionId from role_permission where roleId=#{userId})")
    public List<Permission> findRolesPermiission(String userId);

    //删除角色权限中间表
    @Delete("delete from role_permission where roleid=#{id}")
    void delete_role_permission(String id);

    //删除角色表
    @Delete("delete from role where id=#{id}")
    void delete_role(String id);

    //删除角色用户中间表
    @Delete("delete from users_role where roleid=#{id}")
    void delete_users_role(String id);
}
