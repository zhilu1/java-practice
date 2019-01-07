package com.practice.management.service;

import com.practice.management.domain.SysPermission;
import com.practice.management.domain.SysUser;
import com.practice.management.domain.SysUser;

import java.util.List;

public interface UserService {
    SysUser getUserByUserName(String userName);

    SysUser getUserById(Integer id);

    List<SysUser> getAll();

    boolean updateUser(SysUser user);

    boolean createUser(SysUser user);

    int deleteUserById(Integer id);

    List<SysPermission> getPermissionsByUserId(Integer id);

    int addRoleToUser(Integer userId, Integer roleId);

    void clearRoles(Integer userId);
}
