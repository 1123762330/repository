package com.itheima.dao;

import com.itheima.SysLog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISysLogDao {
    @Insert("insert into syslog(visitTime,username,ip,url,executionTime,method) values(#{visitTime},#{username},#{ip},#{url},#{executionTime},#{method})")
    public void save(SysLog sysLog);


    @Select("select * from syslog Order By visitTime Desc")
    List<SysLog> findAll();

    //删除日志
    @Delete("delete from syslog where id=#{id}")
    void delete(@Param("id") String id);
}
