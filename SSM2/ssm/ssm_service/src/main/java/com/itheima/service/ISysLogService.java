package com.itheima.service;


import com.itheima.SysLog;

import java.util.List;

public interface ISysLogService {
    //添加日志
    public void save(SysLog sysLog);

    //查看日志
    List<SysLog> findAll(int page, int size);

    //删除日志
    void delete(String[] sysLogs);



}
