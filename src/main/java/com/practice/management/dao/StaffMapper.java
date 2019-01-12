package com.practice.management.dao;

import com.practice.management.domain.Staff;

public interface StaffMapper {
    Staff selectById(String username);
}
