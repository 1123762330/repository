package com.itheima.dao;

import com.itheima.Role;
import com.itheima.UserInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserDao {
    @Select("select * from users where username=#{username}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "phoneNum", column = "phoneNum"),
            @Result(property = "status", column = "status"),
            @Result(property = "roles",column = "id",javaType = java.util.List.class,many = @Many(select = "com.itheima.dao.IRoleDao.findRoleByUserId"))
    })
    public UserInfo findByUsername(String username);

    //查询所有的管理员用户
    @Select("select * from users")
    public List<UserInfo> findAll();

    //添加管理员账户
    @Insert("insert into users(email,username,password,phoneNum,status) values(#{email},#{username},#{password},#{phoneNum},#{status})")
    public void save(UserInfo user);

    //根据用户id查询用户详情
    @Select("select * from users where id=#{userId}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "phoneNum", column = "phoneNum"),
            @Result(property = "status", column = "status"),
            @Result(property = "roles",column = "id",javaType = java.util.List.class,many = @Many(select = "com.itheima.dao.IRoleDao.findRoleByUserId"))
    })
    public UserInfo findById(String userId);

    //查询用户以及用户可以添加的角色
    @Select("select * from role where id not in (select roleId from users_role where userId=#{userId})")
    public List<Role> findOtherRoles(String userId);

    //给用户添加角色
    @Insert("insert into users_role(userId,roleId) values(#{userId},#{roleId})")
    void addRoleToUser(@Param("userId") String userId, @Param("roleId") String roleId);

    //删除用户角色中间表
    @Delete("delete from users_role where userid=#{userId}")
    void delete_users_role(String userId);

    //删除用户
    @Delete("delete from users where id=#{userId}")
    void delete_users(String userId);

}
