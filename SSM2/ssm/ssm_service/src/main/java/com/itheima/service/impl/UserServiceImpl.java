package com.itheima.service.impl;

import com.github.pagehelper.PageHelper;
import com.itheima.Role;
import com.itheima.UserInfo;
import com.itheima.dao.IUserDao;
import com.itheima.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements IUserService{

    @Autowired
    private IUserDao userDao;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userDao.findByUsername(username);
        //处理自己的用户对象封装成UserDetails
        //User user = new User(userInfo.getUsername(),"{noop}"+userInfo.getPassword(),getAuthority(userInfo.getRoles()));
        User user = new User(userInfo.getUsername(), userInfo.getPassword(), userInfo.getStatus() == 0 ? false : true, true, true, true, getAuthority(userInfo.getRoles()));
        return user;
    }

    //返回一个List集合,集合中装入的是角色认证
    public List<SimpleGrantedAuthority> getAuthority(List<Role> roles){
        List<SimpleGrantedAuthority> list=new ArrayList<SimpleGrantedAuthority>();
        for(Role role:roles){
            list.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
        }
        return list;
    }

    //查询所有管理用户
    @Override
    public List<UserInfo> findAll(int page, int size) {
        System.out.println("findAll方法执行到了...");
        //调用分页插件,实现分页功能
        PageHelper.startPage(page,size);
        return userDao.findAll();
    }

    //添加用户
    @Override
    public void save(UserInfo user) {
        //对密码进行加密管理
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    @Override
    public UserInfo findById(String userId) {
        return userDao.findById(userId);
    }

    @Override
    public List<Role> findOtherRoles(String userId) {
        return userDao.findOtherRoles(userId);
    }

    @Override
    //给用户添加角色
    public void addRoleToUser(String userId, String[] roleIds) {
        for (String roleId:roleIds) {
            userDao.addRoleToUser(userId,roleId);
        }
    }

    @Override
    public void delete(String userId) {
        userDao.delete_users_role(userId);
        userDao.delete_users(userId);
    }

}


















