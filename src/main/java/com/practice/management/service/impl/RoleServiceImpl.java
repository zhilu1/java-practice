//package com.practice.management.service.impl;
//
//import com.practice.management.dao.SysRoleMapper;
//import com.practice.management.domain.SysRole;
//import com.practice.management.dto.RoleDTO;
//import com.practice.management.service.RoleService;
//import org.assertj.core.util.Preconditions;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class RoleServiceImpl implements RoleService {
//
//    @Autowired
//    SysRoleMapper roleDao;
//
//    @Override
//    public RoleDTO getRoleById(Integer id) {
//        RoleDTO dto = new RoleDTO();
//        SysRole role = roleDao.getById(id);
//        Preconditions.checkNotNull(role, "该角色不存在");
//        BeanUtils.copyProperties(role, dto);
//        return dto;
//    }
//}
