package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Favorites;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

public class FavoriteDaoImpl implements FavoriteDao {
    //定义一个成员变量
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Favorite findByRidAndUid(int rid, int uid) {
        Favorite favorite = null;
        try {
            String sql = " select * from tab_favorite where rid = ? and uid = ?";
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);

        } catch (DataAccessException e) {
            System.out.println("findByRidAndUid查询异常!!!");
        }
        return favorite;
    }

    @Override
    public int findCountByRid(int rid) {
        String sql = "SELECT COUNT(*) FROM tab_favorite WHERE rid = ?";
        return template.queryForObject(sql, Integer.class, rid);
    }

    //添加收藏列表
    @Override
    public void add(int rid, int uid) {
        String sql = "insert into tab_favorite values(?,?,?)";
        template.update(sql, rid, new Date(), uid);
    }

    //收藏列表查询
    @Override
    public List<Route> findFavorite(int uid) {
        List RouteList = null;
        try {
            String sql = "SELECT * FROM tab_favorite f LEFT JOIN tab_route r ON f.rid = r.rid where uid=?";
            RouteList = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), uid);
        } catch (DataAccessException e) {
            System.out.println("findFavorite查询异常!!!");
        }
        return RouteList;
    }

    //取消收藏功能
    @Override
    public void cancel(int rid, int uid) {
        String sql = "delete from tab_favorite where rid = ? and uid = ? ";
        template.update(sql, rid, uid);
    }

}
