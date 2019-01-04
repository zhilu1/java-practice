package com.practice.management.service.impl;

import com.github.pagehelper.util.StringUtil;
import com.practice.management.dao.SysPermissionMapper;
import com.practice.management.dao.SysUserMapper;
import com.practice.management.domain.SysUser;
import com.practice.management.dto.PermissionDTO;
import com.practice.management.dto.UserDTO;
import com.practice.management.service.UserService;
import org.assertj.core.util.Preconditions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    SysUserMapper userDao;

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
    public UserDTO updateUser(UserDTO user) {
        return null;
    }

    @Override
    public boolean createUser(UserDTO user) {
        Preconditions.checkNotNull(user, "入参为空");
        Preconditions.checkArgument(!StringUtil.isEmpty(user.getUsername()), "账户为空");
        Preconditions.checkArgument(!StringUtil.isEmpty(user.getName()), "用户姓名为空");
        Preconditions.checkArgument(!StringUtil.isEmpty(user.getPassword()), "用户密码为空");
        Preconditions.checkArgument(!StringUtil.isEmpty(user.getDepartment()), "用户所属部门为空");

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
        permissionDao.getByUserId(id);
        return null;
    }
}
