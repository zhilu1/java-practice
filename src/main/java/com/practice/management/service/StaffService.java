package com.practice.management.service;

import com.practice.management.domain.Staff;

import java.util.List;

public interface StaffService {
    List<Staff> selectStaffs();

    Staff selectById(String username);
}
