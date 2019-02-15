package com.practice.management.service.impl;

import com.practice.management.domain.Staff;
import com.practice.management.dao.StaffMapper;
import com.practice.management.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    StaffMapper staffMapper;

    @Override
    public List<Staff> selectStaffs() {
        return staffMapper.selectStaffs();
    }

    @Override
    public Staff selectById(String username){
        Staff selectedStaff = staffMapper.selectById(username);
        return selectedStaff;
    }
}
