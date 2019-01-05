package com.practice.management.service;

import com.practice.management.dto.RoleDTO;

public interface RoleService {
    RoleDTO getRoleById(Integer id);

    void clearPermissions(Integer roleId);

    int addPermissionToRole(Integer roleId, Integer permissionId);
}
