package com.practice.management.service.impl;

import com.practice.management.dao.SysPermissionMapper;
import com.practice.management.domain.SysPermission;
import com.practice.management.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    SysPermissionMapper permissionDao;

    @Override
    public List<SysPermission> getAll() {
        return permissionDao.getAll();
    }

}
