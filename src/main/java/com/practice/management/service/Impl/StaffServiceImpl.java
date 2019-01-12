package com.practice.management.service.Impl;

import com.practice.management.domain.Staff;
import com.practice.management.dao.StaffMapper;
import com.practice.management.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    StaffMapper staffMapper;

    @Override
    public Staff selectById(String username){
        Staff selectedStaff = staffMapper.selectById(username);
        return selectedStaff;
    }
}
