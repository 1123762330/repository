package com.itheima.service.impl;

import com.github.pagehelper.PageHelper;
import com.itheima.Orders;
import com.itheima.dao.IOrderDao;
import com.itheima.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("orderService")
@Transactional
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private IOrderDao orderDao;

    @Override
    public List<Orders> findAll(int page,int size) {
        //调用分页插件,实现分页功能
        PageHelper.startPage(page,size);
        return orderDao.findAll();
    }

    @Override
    public Orders findById(String ordersId) {
        return orderDao.findById(ordersId);
    }
}
