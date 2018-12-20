package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    //定义一个成员变量
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    //查询总记录数
    @Override
    public int findTotalCount(int cid, String rname) {
            /*String sql="select count(*) from tab_route where cid=?";
            Integer i = template.queryForObject(sql, Integer.class, cid);*/

        //定义一个模板sql
        String sql = "select count(*) from tab_route where 1=1";
        StringBuilder sb = new StringBuilder(sql);
        //定义一个集合用来存储所有的条件
        List params = new ArrayList();
        //判断参数是否有值
        if (cid != 0) {
            sb.append(" and cid = ? ");
            params.add(cid);//添加？对应的值
        }

        if (rname != null && rname.length() > 0 && !"null".equals(rname)) {
            sb.append(" and rname like ? ");

            params.add("%" + rname + "%");
        }
        sql = sb.toString();
        return template.queryForObject(sql, Integer.class, params.toArray());
    }

    //查询分页的集合数据
    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
       /* String sql = "select * from tab_route where cid=? and rname like ? limit ?,?";
        List<Route> list = template.query(sql, new BeanPropertyRowMapper<>(Route.class), cid, start, pageSize);*/

        //定义一个模板sql
        String sql = "select * from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        //定义一个集合用来存储所有的条件
        List params = new ArrayList();
        //判断参数是否有值
        if (cid != 0) {
            sb.append(" and cid = ? ");

            params.add(cid);//添加？对应的值
        }

        if (rname != null && rname.length() > 0 && !"null".equals(rname)) {
            sb.append(" and rname like ? ");

            params.add("%" + rname + "%");
        }

        sb.append(" limit ? , ? ");//分页条件查询
        sql = sb.toString();
        params.add(start);//起始页码
        params.add(pageSize);//每页显示的条数
        return template.query(sql, new BeanPropertyRowMapper<>(Route.class), params.toArray());
    }

    //查询详情id
    @Override
    public Route findOne(int rid) {
        String sql = "select * from tab_route where rid=?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
    }
}
