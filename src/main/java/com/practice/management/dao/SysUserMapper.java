package com.practice.management.dao;

import com.practice.management.domain.SysRole;
import com.practice.management.domain.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface SysUserMapper {

    SysUser getByUserName(@Param("username") String username);

    SysUser getById(@Param("id") Integer id);

    List<SysUser> getAll();

    int updateUser(@Param("user") SysUser user);

    int createUser(@Param("user") SysUser user);

    int deleteById(@Param("id") Integer id);


    int addRoleToUser(@Param("rid") Integer rid , @Param("uid") Integer uid);

    int removeRoleFromUser(@Param("rid") Integer rid , @Param("uid") Integer uid);
}
