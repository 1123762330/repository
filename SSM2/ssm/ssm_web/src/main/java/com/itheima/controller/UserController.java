package com.itheima.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.Role;
import com.itheima.UserInfo;
import com.itheima.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    //查询全部管理员
    @RequestMapping("/findAll.do")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView findAll(@RequestParam(name="page",required = true,defaultValue = "1")Integer page, @RequestParam(name="size",required = true,defaultValue = "4")Integer size) {
        System.out.println("查询方法进入");
        ModelAndView mv = new ModelAndView();
        List<UserInfo> list = userService.findAll(page,size);
        PageInfo pageInfo=new PageInfo(list);
        mv.addObject("userList", pageInfo);
        mv.setViewName("user-list");
        return mv;
    }

    //添加管理员账号
    //用户添加
    @RequestMapping("/save.do")
    @PreAuthorize("authentication.principal.username == 'tom'")
    public String save(UserInfo user) {
        userService.save(user);
        return "redirect:findAll.do";
    }

    //根据id查询管理员详情
    @RequestMapping("/findById.do")
    public ModelAndView findById(@RequestParam(name = "id", required = true) String userId){
        System.out.println("查询订单详情执行了...");
        ModelAndView mv = new ModelAndView();
        UserInfo user = userService.findById(userId);
        System.out.println("用户详情:"+user);
        mv.addObject("user",user);
        mv.setViewName("user-show");
        return mv;
    }

    //查询用户以及用户可以添加的角色
    @RequestMapping("/findUserByIdAndAllRole.do")
    public ModelAndView findUserByIdAndAllRole(@RequestParam(name = "id", required = true) String userId){
        ModelAndView mv = new ModelAndView();
        //1.根据用户id查询用户
       UserInfo user=userService.findById(userId);
       //2.根据用户id查询可以添加的角色
        List<Role> otherRoles=userService.findOtherRoles(userId);
        mv.addObject("user",user);
        mv.addObject("roleList",otherRoles);
        mv.setViewName("user-role-add");
        return mv;
    }

    //给用户添加角色
    @RequestMapping("/addRoleToUser.do")
    public String addRoleToUser(@RequestParam(name = "userId", required = true) String userId, @RequestParam(name = "ids", required = true) String[] roleIds) {
        userService.addRoleToUser(userId, roleIds);
        return "redirect:findAll.do";
    }
}
