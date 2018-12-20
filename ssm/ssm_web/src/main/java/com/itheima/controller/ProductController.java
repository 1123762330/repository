package com.itheima.controller;

import com.itheima.Product;
import com.itheima.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService productService;

    //查询全部产品
    @RequestMapping("/findAll.do")
    public ModelAndView findAll() {
        System.out.println("查询方法进入");
        ModelAndView mv = new ModelAndView();
        List<Product> list = productService.findAll();
        mv.addObject("productList", list);
        mv.setViewName("product-list");
        return mv;
    }

    //添加产品
    @RequestMapping("/save.do")
    public void save(Product product, HttpServletRequest request, HttpServletResponse response) throws IOException {
        productService.save(product);
        response.sendRedirect(request.getContextPath()+"/product/findAll.do");
        return;
    }
}
