package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Favorites;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface FavoriteDao {
    //根据rid和uid查询收藏信息
    public Favorite findByRidAndUid(int rid,int uid);

    //根据rid查询收藏总量
    public int findCountByRid(int rid);

    //点击收藏
    public void add(int rid,int uid);

    //点击取消收藏
    public void cancel(int rid, int uid);

    //查找用户收藏
    public List<Route> findFavorite(int uid);


}
