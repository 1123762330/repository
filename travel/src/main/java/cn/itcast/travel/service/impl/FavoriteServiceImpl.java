package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Favorites;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;

import java.util.List;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao=new FavoriteDaoImpl();
    private RouteDao routeDao = new RouteDaoImpl();
    //判断路线是否收藏
    @Override
    public boolean isFavorite(String rid, int uid) {
        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);
        return favorite != null;//如果对象有值，则为true，反之，则为false
    }

    //点击收藏功能
    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid),uid);
    }

    //点击取消收藏功能
    @Override
    public void cancel(String rid, int uid) {
        favoriteDao.cancel(Integer.parseInt(rid),uid);
    }

    //查找用户收藏
    @Override
    public List<Route> findFavorite(int uid) {
        List<Route> list = favoriteDao.findFavorite(uid);
        return list;
    }





}
