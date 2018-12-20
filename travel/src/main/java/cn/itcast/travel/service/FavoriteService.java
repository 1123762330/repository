package cn.itcast.travel.service;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Favorites;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface FavoriteService {
    //判断是否收藏
    public boolean isFavorite(String rid,int uid);

    //点击收藏
    public void add(String rid, int uid);

    //点击取消收藏
    public void cancel(String rid, int uid);

    //查找用户收藏
    public List<Route> findFavorite(int uid);


}
