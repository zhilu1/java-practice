package com.practice.management.service;

import com.practice.management.domain.MonthStatu;

import java.util.List;

public interface MonthService {
    MonthStatu selectByIdAndMonth(String staffid,String year,String month);

    List<MonthStatu> selectByMonth(String year,String month);

//    MonthStatu updateStatu(MonthStatu monthStatu);
}
