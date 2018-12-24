package com.itheima.service.impl;

import com.github.pagehelper.PageHelper;
import com.itheima.Permission;
import com.itheima.Role;
import com.itheima.dao.IRoleDao;
import com.itheima.dao.IUserDao;
import com.itheima.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("roleService")
@Transactional
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private IRoleDao roleDao;
    @Override
    public List<Role> findAll(int page, int size) {
        //调用分页插件,实现分页功能
        PageHelper.startPage(page,size);
        return roleDao.findAll();
    }

    @Override
    public void save(Role role) {
        roleDao.save(role);
    }

    @Override
    public Role findById(String roleId) {
        return roleDao.findById(roleId);
    }

    //根据roleid查询可以添加的权限
    @Override
    public List<Permission> findOtherRoles(String roleId) {
        return roleDao.findOtherRoles(roleId);
    }

    //给角色添加权限
    @Override
    public void addPermissionToRole(String roleId, String[] permissionIds) {
        for (String permissionId : permissionIds) {
            roleDao.addPermissionToRole(roleId,permissionId);
        }
    }

    @Override
    public List<Permission> findRolesPermiission(String roleId) {
        return roleDao.findRolesPermiission(roleId);
    }

    @Override
    public void deleteRole(String id) {
        roleDao.delete_role_permission(id);
        roleDao.delete_users_role(id);
        roleDao.delete_role(id);
    }
}
