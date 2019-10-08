package com.practice.management.dao;

import com.practice.management.domain.MonthStatu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MonthMapper {
    void addStatu(MonthStatu monthStatu);

    void updateStatu(MonthStatu monthStatu);

    int selectByIdAndMonth(String staffId,String year,String month);

    MonthStatu selectMonth(@Param("staffId")String staffId, @Param("year")String year, @Param("month")String month);

    List<MonthStatu> selectByMonth(@Param("year")String year,@Param("month")String month);

}
