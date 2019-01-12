package com.practice.management.dao;

import com.practice.management.domain.SysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SysPermissionMapper {

    List<SysPermission> getAll(); //get all types of permissions

    List<SysPermission> getByUserId(@Param("uid") Integer uid);

    SysPermission getById(@Param("id") Integer id);

// Followings NOT yet implmented
    List<SysPermission> getByRoleId(@Param("rid") Integer rid);

    int addPermission(@Param("permission") SysPermission permission);

    int updatePermission(@Param("permission") SysPermission permission);

    SysPermission deletePermission(@Param("id") Integer id);
}
