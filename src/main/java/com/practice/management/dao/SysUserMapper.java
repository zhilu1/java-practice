package com.practice.management.dao;

import com.practice.management.domain.SysUser;
import org.apache.ibatis.annotations.Param;

public interface SysUserMapper {
    SysUser findByUserName(@Param("username") String username);
}
