package com.practice.management.dao;

import com.practice.management.domain.OfficeDate;

import java.util.List;

public interface CalendarMapper {
    List<OfficeDate> selectAllOfficeDates();
}
