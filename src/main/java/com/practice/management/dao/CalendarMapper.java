package com.practice.management.dao;

import com.practice.management.domain.OfficeDate;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.util.List;

public interface CalendarMapper {
    List<OfficeDate> selectAllOfficeDates();

    int createOfficeDate(@Param("officeDate") OfficeDate officeDate);

    OfficeDate getOfficeDateByDate(@Param("onDate") Date date);

    int updateOfficeDateById(@Param("officeDate")OfficeDate date, @Param("id")Integer id);
}
