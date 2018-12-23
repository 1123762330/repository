package com.itheima.service;

import com.itheima.Product;
import com.itheima.Role;
import com.itheima.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserService extends UserDetailsService {
    //查询所有的产品信息
    public List<UserInfo> findAll(int page, int size);

    //添加管理员用户
    public void save(UserInfo user);

    //根据用户id查询用户详情
    public UserInfo findById(String userId);

    //查询用户以及用户可以添加的角色
    List<Role> findOtherRoles(String userId);

    //给用户添加角色
    void addRoleToUser(String userId, String[] roleIds);
}
