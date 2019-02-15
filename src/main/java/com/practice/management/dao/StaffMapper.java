package com.practice.management.dao;

import com.practice.management.domain.Staff;

import java.util.List;

public interface StaffMapper {
    List<Staff> selectStaffs();

    Staff selectById(String username);
}
