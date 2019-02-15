package com.practice.management.service;

import com.practice.management.domain.Record;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface RecordService {

    List<Record> selectById(String staffId);
    List<Record> selectRecords();
    List<Record> selectByDayInAMonth(String year,String month);
    List<Record> selectByIdAndDayInAMonth(String staffId,String year,String month);
    Record selectByIdAndRiqi(String staffId, Date riqi);
    boolean batchImport(String fileName, MultipartFile file) throws Exception;
    String judgeStatu(Date riqi,String sbtime,String xbtime);
    void updateStatu(Record record);
    Date judgeMinDateById(List<Record> records);
    Date judgeMaxDate(List<Record> records);
}
