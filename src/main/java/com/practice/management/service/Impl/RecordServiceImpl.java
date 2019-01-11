package com.practice.management.service.Impl;

import com.practice.management.domain.Record;
import com.practice.management.common.MyException;
import com.practice.management.dao.RecordMapper;
import com.practice.management.service.RecordService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordMapper recordMapper;

    @Override
    public List<Record> selectRecords() {
        return recordMapper.selectRecords();
    }

    @Override
    public List<Record> selectByIdAndDate(String id,String year,String month){
        List<Record> selectedRecords = recordMapper.selectByIdAndYearAndMonth(id,year,month);
        return selectedRecords;
    }

    @Transactional(readOnly = false,rollbackFor = Exception.class)
    @Override
    public boolean batchImport(String fileName, MultipartFile file) throws Exception {

        boolean notNull = false;
        List<Record> records = new ArrayList<Record>();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new MyException("上传文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if(sheet!=null){
            notNull = true;
        }
        Record record;
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null){
                continue;
            }

            row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
            String staffId = row.getCell(1).getStringCellValue();

            Date riqi = row.getCell(0).getDateCellValue();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            String sbtime;
//                    = (row.getCell(2) ==null) ?  null : row.getCell(2).getStringCellValue();
            if(row.getCell(2) ==null){
                sbtime = null;
            }
            else{
                Date date1 = row.getCell(2).getDateCellValue();
                SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
                sbtime = sdf1.format(date1);
            }
            String xbtime ;
            if(row.getCell(3) ==null){
                xbtime = null;
            }
            else{
                Date date2 = row.getCell(3).getDateCellValue();
                SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
                xbtime = sdf2.format(date2);
            }

            record = new Record();

            record.setStaffId(staffId);
            record.setRiqi(riqi);
            record.setSbtime(sbtime);
            record.setXbtime(xbtime);

            records.add(record);
        }
        for (Record r : records) {
            String staffid = r.getStaffId();
            Date riqi = r.getRiqi();
            int cnt = recordMapper.selectByIdAndRiqi(staffid,riqi);
            if (cnt == 0) {
                recordMapper.addRecord(r);
                System.out.println(" 插入 "+r);
            } else {
                recordMapper.updateByIdAndRiqi(r);
                System.out.println(" 更新 "+r);
            }
        }
        return notNull;
    }
}
