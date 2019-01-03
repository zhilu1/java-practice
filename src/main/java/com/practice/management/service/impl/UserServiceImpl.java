package com.practice.management.service.impl;

import com.practice.management.dao.SysUserMapper;
import com.practice.management.domain.SysUser;
import com.practice.management.dto.UserDTO;
import com.practice.management.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.NoSuchElementException;

public class UserServiceImpl implements UserService {
    @Autowired
    SysUserMapper userDao;

    @Override
    public UserDTO getUserByUserName(String userName) {
        UserDTO dto = new UserDTO();
        SysUser user = userDao.findByUserName(userName);
        if(user != null){
            BeanUtils.copyProperties(user, dto);
            return dto;
        }
        else{
            throw new IllegalArgumentException("用户名不存在");
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
    public int deleteUserById(Integer id) {
        return 0;
    }
}
