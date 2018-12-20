package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    //定义一个成员变量
    private  JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());

    //查询导航数据的方法
    @Override
    public List<Category> findAll() {
        String sql="select * from tab_category";
        List<Category> list = template.query(sql, new BeanPropertyRowMapper<>(Category.class));
        System.out.println("findAll方法查到的数据为:"+list.toString());
        return list;
    }

    /*@Override
    public List<Category> findAll() {
        String sql="select * from tab_category ORDER BY cid";
        List<Category> list = template.query(sql, new BeanPropertyRowMapper<>(Category.class));
        System.out.println("findAll方法查到的数据为:"+list.toString());
        return list;
    }*/
}
