package com.practice.management.service.impl;

import com.github.pagehelper.util.StringUtil;
import com.practice.management.dao.CalendarMapper;
import com.practice.management.domain.OfficeDate;
import com.practice.management.domain.forms.CalendarForm;
import com.practice.management.service.CalendarService;
import org.assertj.core.util.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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

    @Override
    public OfficeDate getByDate(Date date) {
        OfficeDate res = calendarDao.getOfficeDateByDate(date);
        if(res == null){
            res = new OfficeDate();
            res.setDate(date);
            res.setStatus(0);
        }
        return res;
    }

    @Override
    public List<OfficeDate> getByYearAndMonth(String year, String month) {
        return calendarDao.selectByYearAndMonth(year,month);
    }

    @Override
    public List<OfficeDate> convertFormIntoListDates(CalendarForm calendarForm) throws ParseException {
        Preconditions.checkNotNull(calendarForm.getStatus(),"状态不得为空");
        Time startTime = null;
        Time endTime = null;
        if(calendarForm.getStatus() != 0){
            Preconditions.checkArgument(!StringUtil.isEmpty(calendarForm.getStartTime()), "开始时间不得为空");
            Preconditions.checkArgument(!StringUtil.isEmpty(calendarForm.getEndTime()), "结束时间不得为空");
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
            //convert string to java.util.Date, then java.sql.Time
            startTime = new Time(timeFormat.parse(calendarForm.getStartTime()).getTime());
            endTime = new Time(timeFormat.parse(calendarForm.getEndTime()).getTime());
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        //convert string to java.util.Date, then java.sql.Date
        Date start = new Date(dateFormat.parse(calendarForm.getStartDate()).getTime()) ;
        Date end = new Date(dateFormat.parse(calendarForm.getEndDate()).getTime());


        Calendar cal = new GregorianCalendar();

        List<OfficeDate> res = new ArrayList<>();
        for(cal.setTime(start); start.compareTo(end) <= 0; ){
            if (calendarForm.getIgnoWeekends() && (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
                //ignore weekends
                cal.add(Calendar.DATE, 1);
                start = new Date(cal.getTime().getTime());
                continue;
            }
            if(calendarForm.getIgnoSetDays() && calendarDao.getOfficeDateByDate(start) != null){
                cal.add(Calendar.DATE, 1);
                start = new Date(cal.getTime().getTime());
                continue;
            }
            //add date to list
            OfficeDate day = new OfficeDate();
            day.setDate(start);
            day.setStartTime(startTime);
            day.setEndTime(endTime);
            day.setStatus(calendarForm.getStatus());
            res.add(day);
            cal.add(Calendar.DATE, 1);
            start = new Date(cal.getTime().getTime());
        }

        return res;
    }

    @Override
    public void setOfficeDates(List<OfficeDate> list) {
        for (OfficeDate date: list) {
            //if the date already exist in dataase, overwrite it
            OfficeDate existedDate = calendarDao.getOfficeDateByDate(date.getDate());
            if(existedDate != null){
                calendarDao.updateOfficeDateById(date, existedDate.getId());
            }
            else{
                calendarDao.createOfficeDate(date);
            }
        }
    }
}
