package com.practice.management.service;

import com.practice.management.domain.SysPermission;
import com.practice.management.domain.SysUser;
import com.practice.management.domain.forms.UserForm;

import java.util.List;

public interface UserService {
    SysUser getUserByUserName(String userName);

    SysUser getUserById(Integer id);

    List<SysUser> getAll();

    boolean updateUser(SysUser user);

    boolean createUser(SysUser user);

    int deleteUserByUsername(String username);

    List<SysPermission> getPermissionsByUserId(Integer id);

    int addRoleToUser(Integer userId, Integer roleId);

    UserForm convertUserToForm(SysUser user);

    SysUser convertFormToUser(UserForm form);

}
