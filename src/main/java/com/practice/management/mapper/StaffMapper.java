package com.practice.management.mapper;

import com.practice.management.bean.Staff;

public interface StaffMapper {
    Staff selectById(String username);
}
