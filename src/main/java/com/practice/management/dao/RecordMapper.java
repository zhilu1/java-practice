package com.practice.management.dao;

import com.practice.management.domain.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface RecordMapper {

    List<Record> selectRecords();

    List<Record> selectById(String staffId);

    void addRecord(@Param("record")Record record);

    void updateByIdAndRiqi(@Param("record")Record record);

    int selectByIdAndRiqi(@Param("staffId")String staffId,@Param("riqi") Date riqi);

    List<Record> selectByIdAndYearAndMonth(@Param("staffId")String staffId,@Param("year")String year,@Param("month")String month);

    List<Record> selectByYearAndMonth(@Param("year")String year,@Param("month")String month);

    void updateStatuByIdAndRiqi(@Param("record")Record record);

    Record selectByIdAndDate(@Param("staffId")String staffId,@Param("riqi")Date riqi);
}
