package com.practice.management.service;

import com.practice.management.domain.SysRole;
import com.practice.management.domain.SysUser;

import java.util.List;

public interface RoleService {
    SysRole getRoleById(Integer id);

    void clearPermissions(Integer roleId);

    List<SysRole> getAll();

    int addPermissionToRole(Integer roleId, Integer permissionId);
}
