package com.itheima.service;

import com.itheima.Product;

import java.util.List;

public interface IProductService {
    //查询所有的产品信息
    public List<Product> findAll();

    //添加产品
    public void save(Product product);
}
