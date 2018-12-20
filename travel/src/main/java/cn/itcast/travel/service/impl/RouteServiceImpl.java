package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoimpl;
import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    //声明成员变量
    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao=new RouteImgDaoImpl();
    private SellerDao sellerDao=new SellerDaoimpl();
    private FavoriteDao favoriteDao=new FavoriteDaoImpl();
    //根据类别进行分页查询
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        System.out.println("RouteServiceImpl中的pageQuery方法执行了...");
        //封装PageBean
        PageBean<Route> pb = new PageBean<>();
        //设置当前页码
        pb.setCurrentPage(currentPage);
        //设置每页显示数据条数
        pb.setPageSize(pageSize);
        //设置总记录数
        int totalCount = routeDao.findTotalCount(cid,rname);
        System.out.println("总记录数为:"+totalCount);
        pb.setTotalCount(totalCount);
        //设置当前页显示的数据集合
        int start = (currentPage - 1) * pageSize;//开始的记录数
        List<Route> list = routeDao.findByPage(cid,start,pageSize,rname);
        pb.setList(list);

        //总页数=总记录数/每页条数
        int totalPage=totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        System.out.println("总页数是:"+totalPage);
        pb.setTotalPage(totalPage);
        System.out.println("RouteServiceImpl中的pageQuery方法结束了!!!");
        return pb;

    }

    @Override
    public Route findRoueId(String rid) {
        System.out.println("RouteServiceImpl中的findRoueId方法执行了...");
        //1.根据id去route表中查询route对象
        Route route = routeDao.findOne(Integer.parseInt(rid));
        System.out.println("----查询到的route对象为:"+route);

        //2.根据rid查询图片集合信息
        List<RouteImg> list = routeImgDao.findByRid(route.getRid());
        route.setRouteImgList(list);
        System.out.println("----查询到的图片集合为:"+list);

        //3.根据route的sid(商家id)查询商家对象
        Seller seller = sellerDao.findById(route.getSid());
        System.out.println("----查询到的商家对象是:"+seller);
        route.setSeller(seller);

        //4.查询收藏次数
        int count=favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);
        System.out.println("RouteServiceImpl中的findRoueId方法结束了!!!");
        return route;
    }

}
