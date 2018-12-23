package com.itheima.service.impl;

import com.github.pagehelper.PageHelper;
import com.itheima.SysLog;
import com.itheima.dao.ISysLogDao;
import com.itheima.service.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysLogServiceImpl implements ISysLogService {
    @Autowired
    private ISysLogDao sysLogDao;

    @Override
    public void save(SysLog sysLog) {
        System.out.println("添加日志方法执行了");
        sysLogDao.save(sysLog);
    }

    @Override
    public List<SysLog> findAll(int page,int size) {
        System.out.println("查找所有的日志");
        //调用分页插件,实现分页功能
        PageHelper.startPage(page,size);
        return sysLogDao.findAll();
    }
}
