package com.practice.management.mapper;

import com.practice.management.bean.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface RecordMapper {

    List<Record> selectRecords();

    void addRecord(@Param("record")Record record);

    void updateByIdAndRiqi(@Param("record")Record record);

    int selectByIdAndRiqi(@Param("staffId")String staffId,@Param("riqi") Date riqi);

    List<Record> selectByIdAndYearAndMonth(@Param("staffId")String staffId,@Param("year")String year,@Param("month")String month);

}
