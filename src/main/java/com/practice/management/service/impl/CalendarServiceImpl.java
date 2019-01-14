package com.practice.management.service.impl;

import com.practice.management.dao.CalendarMapper;
import com.practice.management.domain.OfficeDate;
import com.practice.management.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarServiceImpl implements CalendarService {

    @Autowired
    CalendarMapper calendarDao;

    @Override
    public List<OfficeDate> getAllOfficeDates() {
        List<OfficeDate> res = calendarDao.selectAllOfficeDates();
        return res;
    }
}
