package com.practice.management.service;

import com.practice.management.domain.OfficeDate;
import com.practice.management.domain.forms.CalendarForm;

import java.text.ParseException;
import java.util.List;

public interface CalendarService {
    List<OfficeDate> getAllOfficeDates();

    List<OfficeDate> convertFormIntoListDates(CalendarForm calendarForm) throws ParseException;

    void setOfficeDates(List<OfficeDate> list);
}
