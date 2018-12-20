package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    //注册用户
    boolean register(User user, HttpServletRequest request);
    //激活用户
    boolean active(String code);
    //用户登录
    User login(User user);
}
