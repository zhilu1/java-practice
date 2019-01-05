package com.practice.management.service.impl;

import com.github.pagehelper.util.StringUtil;
import com.practice.management.dao.SysPermissionMapper;
import com.practice.management.dao.SysRoleMapper;
import com.practice.management.dao.SysUserMapper;
import com.practice.management.domain.SysPermission;
import com.practice.management.domain.SysRole;
import com.practice.management.domain.SysUser;
import com.practice.management.dto.PermissionDTO;
import com.practice.management.dto.UserDTO;
import com.practice.management.service.UserService;
import org.assertj.core.util.Preconditions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    SysUserMapper userDao;

    @Autowired
    SysRoleMapper roleDao;

    @Autowired
    SysPermissionMapper permissionDao;

    @Override
    public UserDTO getUserByUserName(String userName) {
        UserDTO dto = new UserDTO();
        SysUser user = userDao.getByUserName(userName);
        if(user != null){
            BeanUtils.copyProperties(user, dto);
            return dto;
        }
        else{
            throw new IllegalArgumentException("用户名不存在");
        }
    }

    @Override
    public UserDTO getUserById(Integer id) {
        UserDTO dto = new UserDTO();
        SysUser user = userDao.getById(id);
        if(user != null){
            BeanUtils.copyProperties(user, dto);
            return dto;
        }
        else{
            throw new IllegalArgumentException("用户ID不存在");
        }
    }

    @Override
    public List<UserDTO> getAll() {
        return null;
    }

    @Override
    public boolean updateUser(UserDTO user) {
        Preconditions.checkNotNull(user, "入参为空");
        Preconditions.checkNotNull(userDao.getById(user.getId()), "用户不存在");
        Preconditions.checkNotNull(userDao.getByUserName(user.getUsername()), "用户不存在");
        SysUser bean = new SysUser();
        BeanUtils.copyProperties(user,bean);
//        SysUser oldUser = userDao.getById(user.getId()); 复制非空
        if(userDao.updateUser(bean) >= 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean createUser(UserDTO user) {
        Preconditions.checkNotNull(user, "入参为空");
        Preconditions.checkArgument(!StringUtil.isEmpty(user.getUsername()), "账户为空");
        Preconditions.checkArgument(!StringUtil.isEmpty(user.getName()), "用户姓名为空");
        Preconditions.checkArgument(!StringUtil.isEmpty(user.getPassword()), "用户密码为空");
        Preconditions.checkArgument(!StringUtil.isEmpty(user.getDepartment()), "用户所属部门为空");
        Preconditions.checkArgument(userDao.getById(user.getId()) != null, "用户ID已存在");
        Preconditions.checkArgument(userDao.getByUserName(user.getUsername()) != null, "账户名已存在");
        SysUser bean = new SysUser();
        BeanUtils.copyProperties(user,bean);
        int success = userDao.createUser(bean);
        if(success > 0){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public int deleteUserById(Integer id) {
        return 0;
    }

    @Override
    public List<PermissionDTO> getPermissionsByUserId(Integer id) {
        UserDTO user = getUserById(id);
        List<SysPermission> permissions = permissionDao.getByUserId(id);
        List<PermissionDTO> dtos = new ArrayList<>();
        for (SysPermission pem: permissions) {
            PermissionDTO dto = new PermissionDTO();
            BeanUtils.copyProperties(pem, dto);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public int addRoleToUser(Integer userId, Integer roleId) {
        Preconditions.checkNotNull(userDao.getById(userId),"该user不存在");
        Preconditions.checkNotNull(roleDao.getById(roleId),"该role不存在");
        return userDao.addRoleToUser(roleId, userId);
    }

    @Override
    public void clearRoles(Integer userId) {
        Preconditions.checkNotNull(userDao.getById(userId),"该user不存在");
        List<SysRole> roles = roleDao.getByUserId(userId);
        for (SysRole role: roles) {
            userDao.removeRoleFromUser(role.getId(), userId);
        }
    }
}
