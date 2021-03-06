package com.itheima.service;

import com.itheima.Permission;
import com.itheima.Product;

import java.util.List;

public interface IPermissionService {
    //查询所有的权限信息
    public List<Permission> findAll(int page, int size);

    //添加权限
    void save(Permission permission);

    //根据角色id查询到权限
    public List<Permission> findPermissionByRoleId(String id);

    //删除权限
    void delete(String id);
}
