package com.practice.management.service;

import com.practice.management.bean.Record;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface RecordService {

    List<Record> selectRecords();
    List<Record> selectByIdAndDate(String id, String year,String month);
    boolean batchImport(String fileName, MultipartFile file) throws Exception;
}
