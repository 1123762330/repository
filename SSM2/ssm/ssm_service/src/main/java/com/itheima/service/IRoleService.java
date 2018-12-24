package com.itheima.service;

import com.itheima.Permission;
import com.itheima.Role;

import java.util.List;

public interface IRoleService {
    //查询所有的角色信息
    public List<Role> findAll(int page, int size);

    //添加角色
    void save(Role role);

    //根据roleId查询role
    Role findById(String roleId);

    //根据roleid查询可以添加的权限
    List<Permission> findOtherRoles(String roleId);

    //给角色添加权限
    void addPermissionToRole(String roleId, String[] permissionIds);

    //查询角色名下的相关权限
    List<Permission> findRolesPermiission(String roleId);

    //删除角色
    void deleteRole(String id);



}
