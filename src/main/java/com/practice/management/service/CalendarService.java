package com.practice.management.service;

import com.practice.management.domain.OfficeDate;
import com.practice.management.domain.forms.CalendarForm;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

public interface CalendarService {
    List<OfficeDate> getAllOfficeDates();

    OfficeDate getByDate(Date date);
    // Year: yyyy, Month: MM
    List<OfficeDate> getByYearAndMonth(String year, String month);

    List<OfficeDate> convertFormIntoListDates(CalendarForm calendarForm) throws ParseException;
    void setOfficeDates(List<OfficeDate> list);

}
