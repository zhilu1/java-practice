package com.practice.management.service.impl;

import com.github.pagehelper.util.StringUtil;
import com.practice.management.dao.SysPermissionMapper;
import com.practice.management.dao.SysRoleMapper;
import com.practice.management.domain.SysPermission;
import com.practice.management.domain.SysRole;
import com.practice.management.service.RoleService;
import org.assertj.core.util.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    SysRoleMapper roleDao;
    @Autowired
    SysPermissionMapper permissionDao;

    @Override
    public SysRole getRoleById(Integer id) {
        SysRole role = roleDao.getById(id);
        Preconditions.checkNotNull(role, "该角色不存在");
        return role;
    }

    @Override
    public void clearPermissions(Integer roleId) {
        Preconditions.checkNotNull(roleDao.getById(roleId),"该role不存在");
        roleDao.clearPermissions(roleId);
    }

    @Override
    public List<SysRole> getAll() {
        return roleDao.getAll();
    }

    @Override
    public int addPermissionToRole(Integer roleId, Integer permissionId) {
        Preconditions.checkNotNull(permissionDao.getById(permissionId),"该权限不存在");
        Preconditions.checkNotNull(roleDao.getById(roleId),"该role不存在");
        return roleDao.addPermissionToRole(roleId, permissionId);
    }

    @Override
    public int updateRole(Integer roleId, String name) {
        Preconditions.checkArgument(! StringUtil.isEmpty(name),"角色名不得为空");
        Preconditions.checkNotNull(roleDao.getById(roleId),"该role不存在");
        SysRole role = new SysRole();
        role.setName(name);
        role.setId(roleId);
        return roleDao.updateRole(role);
    }

    @Override
    public List<SysPermission> getPermissionsById(Integer roleId) {
        Preconditions.checkNotNull(roleDao.getById(roleId),"该role不存在");
        return roleDao.getPermissionsById(roleId);
    }

    @Override
    public void deleteRoleById(Integer roleId) {
        Preconditions.checkNotNull(roleDao.getById(roleId),"该role不存在");
        roleDao.deleteRoleById(roleId);
    }

    @Override
    public Integer createRole(String name, List<Integer> permissions) {
        Preconditions.checkArgument(! StringUtil.isEmpty(name),"角色名不得为空");
        SysRole role = new SysRole();
        role.setName(name);
        roleDao.createRole(role);
        return role.getId();
    }
}
