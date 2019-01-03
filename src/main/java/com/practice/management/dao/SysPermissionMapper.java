package com.practice.management.dao;

import com.practice.management.domain.SysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SysPermissionMapper {
    List<SysPermission> findAll(); //get all types of permissions

    List<SysPermission> findByUserId(Integer id);
}
