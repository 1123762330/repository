package com.itheima.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.Orders;
import com.itheima.Product;
import com.itheima.service.IOrderService;
import com.itheima.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    /*//查询全部产品(未分页)
    @RequestMapping("/findAll.do")
    public ModelAndView findAll() {
        System.out.println("查询方法进入");
        ModelAndView mv = new ModelAndView();
        List<Orders> list = orderService.findAll();
        mv.addObject("ordersList", list);
        mv.setViewName("orders-list");
        return mv;
    }*/

    //查询产品列表(实现分页)
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name="page",required = true,defaultValue = "1")int page,@RequestParam(name="size",required = true,defaultValue = "4")int size) {
        ModelAndView mv = new ModelAndView();
        List<Orders> lists = orderService.findAll(page,size);
        //PageInfo就是一个分页Bean
        PageInfo pageInfo=new PageInfo(lists);
        mv.addObject("pageInfo",pageInfo);
        mv.setViewName("page_List");
        return mv;
    }

    //根据id查询订单详情
    @RequestMapping("/findById.do")
    public ModelAndView findById(@RequestParam(name = "id", required = true) String ordersId){
        System.out.println("查询订单详情执行了...");
        ModelAndView mv = new ModelAndView();
        Orders orders = orderService.findById(ordersId);
        System.out.println("订单详情:"+orders);
        mv.addObject("orders",orders);
        mv.setViewName("orders-show");
        return mv;
    }
}
