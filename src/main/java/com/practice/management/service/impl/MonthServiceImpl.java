package com.practice.management.service.impl;

import com.practice.management.dao.MonthMapper;
import com.practice.management.domain.MonthStatu;
import com.practice.management.service.MonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonthServiceImpl implements MonthService {

    @Autowired
    private MonthMapper monthMapper;

    @Override
    public MonthStatu selectByIdAndMonth(String staffid,String year,String month){
        MonthStatu monthStatu = monthMapper.selectMonth(staffid,year,month);
        return monthStatu;
    }

    @Override
    public List<MonthStatu> selectByMonth(String year,String month){
        List<MonthStatu> monthStatus = monthMapper.selectByMonth(year,month);
        return monthStatus;
    }



}
