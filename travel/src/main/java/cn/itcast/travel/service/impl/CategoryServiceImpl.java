package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class CategoryServiceImpl implements CategoryService {
    //声明categoryDao的成员变量
    private CategoryDao categoryDao = new CategoryDaoImpl();

    //查找所有导航
    @Override
    public List<Category> findAll() {
        //从redis中查询
        //1.获取jedis
        Jedis jedis = JedisUtil.getJedis();
        //2.排序
        /*Set<String> categorys = jedis.zrange("category", 0, -1);*/
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        List<Category> cs = null;
        //3.判断查询的集合是否为空
        if (categorys == null || categorys.size() == 0) {
            System.out.println("从数据库查询...");
            //如果为空,从数据库查询
            cs = categoryDao.findAll();
            //遍历数据,存储到缓存中
            for (int i = 0; i <cs.size() ; i++) {
                Category  categoryValue= cs.get(i);
                jedis.zadd("category",categoryValue.getCid(),categoryValue.getCname());
                System.out.println("jedis中的数据有:"+categoryValue);
            }
        }else {
            System.out.println("从缓存中查询...");
            //4.如果不为空,将set数据存入到list中
             cs = new ArrayList<>();
             //遍历集合,获取键
            for (Tuple name : categorys) {
                Category category = new Category();
                category.setCname(name.getElement());
                category.setCid((int)name.getScore());
                cs.add(category);
            }
            System.out.println("category的List集合数据为:"+cs);
        }
        return cs;
    }


    //查找所有导航(简化版)
    /*@Override
    public List<Category> findAll() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        //1.从redis中查询
        //1.1获取jedis客户端
        Jedis jedis = JedisUtil.getJedis();
        //1.3查询sortedset中的分数(cid)和值(cname)   //在dao排序已完成
        String json = jedis.get("category2");
        System.out.println("-------redis---"+json);
        List<Category> cs = null;
        //2.判断查询的集合是否为空
        if (json == null || json.length() == 0) {
            System.out.println("从数据库查询....");
            //3.如果为空,需要从数据库查询,在将数据存入redis
            //3.1 从数据库查询
            cs = categoryDao.findAll();
            System.out.println("------------" + cs);
            String csJson = mapper.writeValueAsString(cs);
            System.out.println("------" + cs);
            //3.2 将集合数据存储到redis中的 category的key
            jedis.set("category2",csJson);
        } else {
            System.out.println("从缓存中查询.....");
            //4.如果不为空,数据存入list
            cs = mapper.readValue(json,List.class);
        }
        return cs;
    }*/



}
