package com.itheima.controller;


import com.github.pagehelper.PageInfo;
import com.itheima.Permission;
import com.itheima.Role;
import com.itheima.UserInfo;
import com.itheima.service.IRoleService;
import com.itheima.service.IUserService;
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
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;
    //给角色添加权限
    @RequestMapping("/addPermissionToRole.do")
    public String addPermissionToRole(@RequestParam(name = "roleId", required = true) String roleId, @RequestParam(name = "ids", required = true) String[] permissionIds) throws Exception {
        roleService.addPermissionToRole(roleId, permissionIds);
        return "redirect:findAll.do";
    }

    //根据roleId查询出role,并查询出可以添加的权限
    @RequestMapping("/findRoleByIdAndAllPermission.do")
    public ModelAndView findRoleByIdAndAllPermission(@RequestParam(name = "id", required = true) String roleId){
        ModelAndView mv = new ModelAndView();
        //1.根据roleid查询role
        Role role=roleService.findById(roleId);
        //2.根据roleid查询可以添加的权限
        List<Permission> otherPermissions=roleService.findOtherRoles(roleId);
        mv.addObject("role",role);
        mv.addObject("permissionList",otherPermissions);
        mv.setViewName("role-permission-add");
        return mv;
    }

    //查询全部角色
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name="page",required = true,defaultValue = "1")Integer page, @RequestParam(name="size",required = true,defaultValue = "4")Integer size) {
        System.out.println("查询方法进入");
        ModelAndView mv = new ModelAndView();
        List<Role> list = roleService.findAll(page,size);
        PageInfo pageInfo=new PageInfo(list);
        mv.addObject("roleList", pageInfo);
        mv.setViewName("role-list");
        return mv;
    }

    //添加角色
    @RequestMapping("/save.do")
    public String save(Role role){
        roleService.save(role);
        return "redirect:findAll.do";
    }
}
