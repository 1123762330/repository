package com.itheima.service;

import com.itheima.Orders;

import java.util.List;

public interface IOrderService {
    //查询所有
    public List<Orders> findAll(int page,int size);

    //根据id查询订单详情
    public Orders findById(String ordersId);
}
