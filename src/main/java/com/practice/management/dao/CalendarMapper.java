package com.practice.management.dao;

import com.practice.management.domain.OfficeDate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CalendarMapper {
    List<OfficeDate> selectAllOfficeDates();

    void createOfficeDate(@Param("officeDate") OfficeDate officeDate);
}
