package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    //声明一个成员变量
    private UserService service = new UserServiceImpl();

    /*注册功能*/
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("UserServlet的regist方法被执行了");
        //调用验证码验证方法
        checkCode(request,response);
        //1.获取数据
        Map<String, String[]> map = request.getParameterMap();
        //2.封装对象
        User regUser = new User();
        try {
            BeanUtils.populate(regUser, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        System.out.println("封装的regUser注册用户:" + regUser.toString());
        //动态获取资源路径
        //String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

        boolean flag = service.register(regUser,request);
        System.out.println("调用注册方法返回的flag:" + flag);
        ResultInfo info = new ResultInfo();
        //4.响应结果
        if (flag) {
            //注册成功
            info.setFlag(true);
        } else {
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败!");
        }

        //将info对象序列化为json并写回客户端
        writeValue(info,response);
        System.out.println("regist方法结束了...");
    }

    /*登录功能*/
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("UserServlet的login方法");
        //调用验证码验证方法
        checkCode(request,response);
        //1.获取用户名
        Map<String, String[]> map = request.getParameterMap();
        //2.封装User对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println("封装的user对象:" + user);

        //3.调用Service查询
        //UserService service = new UserServiceImpl();
        User u = service.login(user);
        System.out.println("调用service查询的用户:" + u);
        //创建显示标记
        ResultInfo info = new ResultInfo();

        //4.判断用户对象是否为null
        if (u == null) {
            //用户名或密码错误
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误!");
        }
        //5.判断用户名是否激活
        if (u != null && !"Y".equals(u.getStatus())) {
            info.setFlag(false);
            info.setErrorMsg("账号还未激活,请先激活!");
        }
        //6.如果用户名激活并且不为空,则登录成功
        if (u != null && "Y".equals(u.getStatus())) {
            request.getSession().setAttribute("user",u);
            info.setFlag(true);
        }
        //将info对象序列化为json并写回客户端
        writeValue(info,response);
        System.out.println("用户登录服务已结束!");
    }

    /*显示用户名功能*/
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("显示登录用户功能进入...");
        //从session中获取登录用户
        Object user = request.getSession().getAttribute("user");
        System.out.println("从session中获取登录用户:" +user);
        //将user写回客户端
        writeValue(user,response);
        System.out.println("显示用户功能结束了....");
    }

    /*退出功能*/
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("退出功能进入了...");
        //销毁session
        request.getSession().invalidate();
        //跳转到登录页面
        response.sendRedirect(request.getContextPath() + "/login.html");
        System.out.println("退出功能结束了...");
    }

    /*邮箱激活功能*/
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("激活功能启动了...");
        //获取激活码
        String code = request.getParameter("code");
        System.out.println("注册获取到的激活码:" + code);
        if (code != null) {
            //2.调用serVice完成激活
            boolean flag = service.active(code);

            //3.判断标记
            String msg = null;
            if (flag) {
                msg = "激活成功,<a href='../login.html'>点击登录</a>";
            } else {
                msg = "激活失败,请联系管理员!";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
        System.out.println("激活用户服务已到末尾!");
    }
}
