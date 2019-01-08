package com.practice.management.dao;

import com.practice.management.domain.SysPermission;
import com.practice.management.domain.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMapper {

    SysRole getById(@Param("id") Integer id);

    List<SysRole> getByUserId(@Param("uid") Integer uid); //get all roles of a user

    List<SysRole> getAll();

    int updateRole(@Param("role") SysRole role);

    int createRole(@Param("role") SysRole role);

    int deleteRoleById(@Param("id") Integer id);

    int addPermissionToRole(@Param("rid") Integer rid, @Param("pid") Integer pid);

    int removePermissionFromRole(@Param("rid") Integer rid, @Param("pid") Integer pid);

    List<SysPermission> getPermissionsById(Integer roleId);
}
