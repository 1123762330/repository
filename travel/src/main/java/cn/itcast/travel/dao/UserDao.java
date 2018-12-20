package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;

public interface UserDao {
    //根据用户查询用户信息
    public User findByUsername(String username);

    //用户保存
    public void save(User user);

    //根据激活码查询用户对象
    User findByCode(String code);

    //激活邮箱
    void updateStatus(User user);

    //登录查询用户名和密码
    User findUsernameAndPassword(String username, String password);
}
