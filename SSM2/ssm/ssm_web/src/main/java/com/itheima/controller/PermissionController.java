package com.itheima.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.Permission;
import com.itheima.Role;
import com.itheima.UserInfo;
import com.itheima.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private IPermissionService permissionService;

    //查询全部权限
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name = "page", required = true, defaultValue = "1") Integer page, @RequestParam(name = "size", required = true, defaultValue = "4") Integer size) {
        System.out.println("查询方法进入");
        ModelAndView mv = new ModelAndView();
        List<Permission> list = permissionService.findAll(page, size);
        PageInfo pageInfo = new PageInfo(list);
        mv.addObject("permissionList", pageInfo);
        mv.setViewName("permission-list");
        return mv;
    }

    //添加权限
    @RequestMapping("/save.do")
    public String save(Permission permission) {
        permissionService.save(permission);
        return "redirect:findAll.do";
    }

    //删除权限
    @RequestMapping("/delete.do")
    public String delete(String id) {
        permissionService.delete(id);
        return "redirect:findAll.do";
    }
}
