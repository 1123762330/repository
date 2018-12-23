package com.itheima.dao;

import com.itheima.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductDao {
    //查询所有的产品信息
    @Select("select * from product")
    public List<Product> findAll();

    //添加功能
    @Insert("insert into product(productNum,productName,cityName,departureTime,productPrice,productDesc,productStatus) values(#{productNum},#{productName},#{cityName},#{departureTime},#{productPrice},#{productDesc},#{productStatus})")
    public void save(Product product);

    //根据id查询产品
    @Select("select * from product where id=#{id}")
    public Product findById(String id);
}
