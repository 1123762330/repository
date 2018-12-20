package com.itheima.service.impl;

import com.itheima.Product;
import com.itheima.dao.IProductDao;
import com.itheima.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("productService")
@Transactional
public class ProductServiceImpl implements IProductService {
    //自动注入
    @Autowired
    private IProductDao productDao;

    @Override
    public List<Product> findAll() {
        System.out.println("service层执行到了...");
        return productDao.findAll();
    }

    @Override
    public void save(Product product) {
        System.out.println("添加功能执行带了...");
        productDao.save(product);
    }
}
