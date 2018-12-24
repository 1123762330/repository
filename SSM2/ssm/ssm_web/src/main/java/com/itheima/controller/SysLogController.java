package com.itheima.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.SysLog;
import com.itheima.service.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/sysLog")
@Controller
public class SysLogController {

    @Autowired
    private ISysLogService sysLogService;

    //删除日志
    @RequestMapping("/delete.do")
    public String deleteRole(@RequestParam(name = "ids", required = true) String[] sysLogs){
        sysLogService.delete(sysLogs);
        return "redirect:findAll.do";
    }

    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name = "page",defaultValue = "1")Integer page,@RequestParam(name = "size",defaultValue = "10")Integer size){
        ModelAndView mv=new ModelAndView();
        List<SysLog> sysLogList= sysLogService.findAll(page,size);
        //分页
        PageInfo pageInfo=new PageInfo(sysLogList);
        mv.addObject("sysLogs",pageInfo);
        mv.setViewName("syslog-list");
        return mv;
    }
}