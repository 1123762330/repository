package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService=new FavoriteServiceImpl();
    //分页查询
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("pageQuery执行了...");
        //接受参数
        String cp = request.getParameter("currentPage");
        String ps = request.getParameter("pageSize");
        String cidstr = request.getParameter("cid");
        String rname = request.getParameter("rname");//接收的线路名称
        //解决乱码
        rname = new String(rname.getBytes("iso-8859-1"), "utf-8");


        System.out.println("----查询到的类别是:" + cidstr + ",页码是" + cp + ",每页显示的条数是:" + ps);
        System.out.println("----查询到的线路名称是:" + rname);
        int cid = 0;//类别id
        //2.处理参数
        if (cidstr != null && cidstr.length() > 0 && !"null".equals(cidstr)) {
            cid = Integer.parseInt(cidstr);
        }

        int currentPage = 0;//当前页码
        if (cp != null && cp.length() > 0 && !"null".equals(cp)) {
            currentPage = Integer.parseInt(cp);
        } else {//默认当前页码为1
            currentPage = 1;
        }

        int pageSize = 0;//每页显示的条数
        if (ps != null && ps.length() > 0 && !"null".equals(ps)) {
            pageSize = Integer.parseInt(ps);
        } else {
            pageSize = 5;
        }

        //3.调用service查询PageBean对象
        PageBean<Route> pb = routeService.pageQuery(cid, currentPage, pageSize, rname);
        //4.将pageBean对象序列化为json,并返回
        writeValue(pb, response);
        System.out.println("pageQuery走了!!!");

    }

    //查询rid的方法
    public void findRouteId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("findRouteId方法执行了...");
        //1.接收id
        String rid = request.getParameter("rid");
        //2.调用service查询route对象
        Route route = routeService.findRoueId(rid);
        //3.转为json写回客户端
        writeValue(route, response);
        System.out.println("findRouteId方法走了!!!");
    }

    //判断当前用户是否收藏过该线路
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("isFavorite服务启动了...");
        //1.获取线路id
        String rid = request.getParameter("rid");
        System.out.println("----查询到的rid是:"+rid);
        //获取当前登录的用户
        User user = (User) request.getSession().getAttribute("user");
        System.out.println("isFavorite服务获取的登录用户是"+user);
        int uid;//用户的idf
        if (user == null) {
            //用户尚未登录
            //uid=0;
            writeValue(false,response);
            return;
        } else {
            //用户已经登录
            uid=user.getUid();
        }
        //3.调用FavoriteService查询是否收藏
        boolean flag = favoriteService.isFavorite(rid,uid);
        //4.写回客户端
        writeValue(flag,response);
        System.out.println("isFavorite结束了!!!");

    }

    //点击收藏
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("addFavorite启动了...");
        //1.获取线路rid
        String rid=request.getParameter("rid");
        //2.获取当前登录的用户
        User user = (User) request.getSession().getAttribute("user");
        System.out.println("----获取当前登录的用户:"+user);
        int uid;//用户id
        if(user==null){
            //用户尚未登录
            return;
        }else{
            //用户已经登录
            uid=user.getUid();
        }
        //3.调用service添加
        favoriteService.add(rid,uid);
        System.out.println("addFavorite结束了!!!");
    }

    //点击取消收藏
    public void cancelFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("cancelFavorite启动了...");
        //1.获取线路rid
        String rid=request.getParameter("rid");
        //2.获取当前登录的用户
        User user = (User) request.getSession().getAttribute("user");
        System.out.println("----获取当前登录的用户:"+user);
        int uid;//用户id
        if(user==null){
            //用户尚未登录
            return;
        }else{
            //用户已经登录
            uid=user.getUid();
        }
        //3.调用service添加
        favoriteService.cancel(rid,uid);
        System.out.println("cancelFavorite结束了!!!");
    }

    //查找用户收藏列表
    public void findfavoriteList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("findfavoriteList启动了...");
        //1.获取当前登录的用户
        User user = (User) request.getSession().getAttribute("user");
        System.out.println("----获取当前登录的用户:"+user);
        int uid;//用户id
        if(user==null){
            //用户尚未登录
            return;
        }else{
            //用户已经登录
            uid=user.getUid();
        }
        //2.调用service查询
        List<Route> list = favoriteService.findFavorite(uid);
        //3.序列化json返回
        writeValue(list,response);
        System.out.println("----查询到的Route集合对象为:"+list);
        System.out.println("findfavoriteList结束了!!!");
    }

}
