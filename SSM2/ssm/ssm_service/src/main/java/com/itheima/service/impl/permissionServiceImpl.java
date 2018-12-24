package com.itheima.service.impl;

import com.github.pagehelper.PageHelper;
import com.itheima.Permission;
import com.itheima.dao.IPermissionDao;
import com.itheima.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("permissionService")
@Transactional
public class permissionServiceImpl implements IPermissionService {
    //自动注入
    @Autowired
    private IPermissionDao permissionDao;

    @Override
    public List<Permission> findAll(int page, int size) {
        //调用分页插件,实现分页功能
        PageHelper.startPage(page,size);
        return permissionDao.findAll();
    }

    @Override
    public void save(Permission permission) {
        permissionDao.save(permission);
    }

    @Override
    public List<Permission> findPermissionByRoleId(String id) {
        return permissionDao.findPermissionByRoleId(id);
    }

    @Override
    public void delete(String id) {
        permissionDao.delete(id);
    }


}
