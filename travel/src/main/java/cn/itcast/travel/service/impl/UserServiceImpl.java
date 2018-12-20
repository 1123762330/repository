package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

import javax.servlet.http.HttpServletRequest;

public class UserServiceImpl implements UserService {
    //声明dao
    private UserDao dao = new UserDaoImpl();

    //注册用户
    @Override
    public boolean register(User user, HttpServletRequest request) {
        //1.根据用户名查询用户对象
        User u = dao.findByUsername(user.getUsername());
        //判断用户名是否为null
        if (u != null) {
            System.out.println("用户名已存在!" + u.getUsername());
            //用户名存在,注册失败
            return false;
        }
        //2.保存用户信息
        //2.1设置激活码
        user.setCode(UuidUtil.getUuid());
        //2.2设置激活状态
        user.setStatus("N");
        dao.save(user);

        //3.激活邮件发送,邮件正文
        System.out.println("激活邮件已发送");
        //获取动态ip
        /*String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        System.out.println("获取到的动态资源路径是:"+path);
        String content = "<a href='"+path+"/user/active?code=" + user.getCode() + "'>点击激活【黑马旅游网】</a>";*/

        String content = "<a href='http://192.168.21.30:80/travel/user/active?code=" + user.getCode() + "'>点击激活【黑马旅游网】</a>";
        MailUtils.sendMail(user.getEmail(), content, "激活邮件");
        return true;
    }

    /*邮件激活*/
    @Override
    public boolean active(String code) {
        //根据激活码查询用户对象
        User user = dao.findByCode(code);
        System.out.println("根据激活码查询用户对象:" + user.toString());
        if (user != null) {
            //修改用户激活状态的方法
            dao.updateStatus(user);
            return true;
        } else {
            System.out.println("通过邮件激活找不到用户名!");
            return false;
        }

    }

    @Override
    public User login(User user) {
        return dao.findUsernameAndPassword(user.getUsername(), user.getPassword());
    }
}
