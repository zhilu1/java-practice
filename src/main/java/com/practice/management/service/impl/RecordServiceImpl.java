package com.practice.management.service.impl;

import com.practice.management.dao.CalendarMapper;
import com.practice.management.dao.MonthMapper;
import com.practice.management.domain.MonthStatu;
import com.practice.management.domain.OfficeDate;
import com.practice.management.domain.Record;
import com.practice.management.common.MyException;
import com.practice.management.dao.RecordMapper;
import com.practice.management.domain.Staff;
import com.practice.management.service.CalendarService;
import com.practice.management.service.RecordService;
import com.practice.management.service.StaffService;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.assertj.core.util.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private MonthMapper monthMapper;

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private StaffService staffService;

    @Override
    public List<Record> selectRecords() {
        return recordMapper.selectRecords();
    }

    @Override
    public List<Record> selectById(String id){
        List<Record> selectedById = recordMapper.selectById(id);
        return selectedById;
    }

    @Override
    public List<Record> selectByDayInAMonth(String year,String month){
        List<Record> recordss = new ArrayList<>();
        List<Staff> staffs = staffService.selectStaffs();
        int year1 =Integer.parseInt(year);
        int month1 =Integer.parseInt(month);
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR,year1);
        a.set(Calendar.MONTH, month1 - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        for(Staff staff:staffs) {
            List<Record> records = recordMapper.selectByIdAndYearAndMonth(staff.getUsername(),year,month);
            for (int i = 1; i <= maxDate; i++) {
                String riqii = year + "-" + month + "-" + i;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date riqi = null;
                try {
                    riqi = sdf.parse(riqii);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int j = 0;
                for (Record r : records) {
                    if (r.getRiqi().equals(riqi)) {
                        j++;
                        recordss.add(r);
                    }
                }
                if (j == 0) {
                    Record record = new Record();
                    record.setStaffId(staff.getUsername());
                    record.setRiqi(riqi);
                    record.setSbtime(null);
                    record.setXbtime(null);
                    record.setStatu(null);
                    recordss.add(record);
                }
            }
        }
        return  recordss;
    }

    @Override
    public List<Record> selectByIdAndDayInAMonth(String staffId,String year,String month){
        List<Record> records = recordMapper.selectByIdAndYearAndMonth(staffId,year,month);
        List<Record> recordss = new ArrayList<>();
        int year1 =Integer.parseInt(year);
        int month1 =Integer.parseInt(month);
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR,year1);
        a.set(Calendar.MONTH, month1 - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        for(int i=1;i<=maxDate;i++){
            String riqii = year +"-"+ month +"-"+ i;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date riqi = null;
            try {
                riqi = sdf.parse(riqii);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int j = 0;
            for(Record r:records){
                if(r.getRiqi().equals(riqi)){
                    recordss.add(r);
                    j++;
                }
            }
            if(j==0){
                Record record = new Record();
                record.setStaffId(staffId);
                record.setRiqi(riqi);
                record.setSbtime(null);
                record.setXbtime(null);
                record.setStatu(null);
                recordss.add(record);
            }
        }
        return  recordss;
    }

    @Override
    public Record selectByIdAndRiqi(String id, Date riqi){
        Record record = recordMapper.selectByIdAndDate(id,riqi);
        return  record;
    }

    @Override
    public String judgeStatu(Date date,String sbtime,String xbtime) {
        OfficeDate officeDate = calendarService.getByDate(new java.sql.Date(date.getTime()));
        java.sql.Date riqi = new java.sql.Date(date.getTime());
        String statu = null;
        if (officeDate.getStatus() == 1) {
            if(sbtime == null || xbtime == null) {
                statu = "忘打卡";
            }else {
                String time = officeDate.getDate() + " " + officeDate.getStartTime();
                String time1 = officeDate.getDate() + " " + officeDate.getEndTime();
                String str = riqi + " " + sbtime + ":00";
                String str1 = riqi + " " + xbtime + ":00";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date cjtime = null;
                Date cjtime1 = null;
                Date sbtime1 = null;
                Date xbtime1 = null;
                try {
                    cjtime = sdf.parse(time);
                    cjtime1 = sdf.parse(time1);
                    sbtime1 = sdf.parse(str);
                    xbtime1 = sdf.parse(str1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (cjtime.before(sbtime1) && xbtime1.before(cjtime1)) {
                    statu = "迟到早退";
                } else if (xbtime1.before(cjtime1)) {
                    statu = "早退";
                } else if (cjtime.before(sbtime1)) {
                    statu = "迟到";
                } else if (sbtime == null && xbtime == null) {
                    statu = "旷工";
                } else {
                    statu = "正常";
                }
            }
        }else if(officeDate.getStatus() == 0){
            if(sbtime != null && xbtime != null){
                statu = "加班";
            }else {
                statu = "放假";
            }
        }
            return statu;
    }

    @Override
    public Date judgeMaxDate(List<Record> records){
        Date maxDate = null;
        for(Record r:records){
            if(!r.getStatu().equals("放假")) {
                if (maxDate == null) {
                    maxDate = r.getRiqi();
                } else if (maxDate.before(r.getRiqi())) {
                    maxDate = r.getRiqi();
                }
            }
        }
        return maxDate;
    }

    @Override
    public Date judgeMinDateById(List<Record> records){
        Date minDate = null;
        for(Record r:records){
            if(!r.getStatu().equals("放假")) {
                if (minDate == null) {
                    minDate = r.getRiqi();
                } else if (minDate.after(r.getRiqi())) {
                    minDate = r.getRiqi();
                }
            }
        }
        return minDate;
    }

    @Override
    public void updateStatu(Record record){
        recordMapper.updateStatuByIdAndRiqi(record);
    }

    @Transactional(readOnly = false,rollbackFor = Exception.class)
    @Override
    public boolean batchImport(String fileName, MultipartFile file) throws Exception {

        boolean notNull = false;
        List<Record> records = new ArrayList<Record>();
        List<Record> recordss = new ArrayList<Record>();
        List<MonthStatu> monthStatus = new ArrayList<>();
        List<Record> recordsss = new ArrayList<Record>();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new MyException("上传文件格式不正确");
        }
//        Preconditions.checkArgument(fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$"),"上传文件格式不正确");
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
        MonthStatu monthStatu;

//      将非工作日中每个员工的状态设为放假
        for(OfficeDate officeDate:calendarService.getAllOfficeDates()){
            if(officeDate.getStatus() == 0){
                for(Staff staff:staffService.selectStaffs()){
                    record = new Record();
                    record.setStaffId(staff.getUsername());
                    record.setRiqi(officeDate.getDate());
                    record.setSbtime(null);
                    record.setXbtime(null);
                    record.setStatu("放假");
                    records.add(record);
                }
            }
        }

        //第一次导入数据库
        for (Record r : records) {
            String staffid = r.getStaffId();
            Date riqi = r.getRiqi();
            int cnt = recordMapper.selectByIdAndRiqi(staffid,riqi);
            if (cnt == 0) {
                recordMapper.addRecord(r);
            } else {
                recordMapper.updateByIdAndRiqi(r);
            }
        }

//      导入excel表中的记录
        int count2 =0;
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null){
                continue;
            }

            Date riqi;
            if(row.getCell(0) != null){
                if(row.getCell(0).getCellType()==0){
                    if(HSSFDateUtil.isCellDateFormatted(row.getCell(0))){
                        riqi = row.getCell(0).getDateCellValue();
                    }else{
                        System.out.println("导入失败(第"+(r+1)+"行,日期格式不正确)");
                        count2 ++;
                        continue;
                    }
                }else{
                    System.out.println("导入失败(第"+(r+1)+"行,日期格式不正确)");
                    count2 ++;
                    continue;
                }
            }else{
                System.out.println("导入失败(第"+(r+1)+"行,未输入日期)");
                count2 ++;
                continue;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("MM");
            SimpleDateFormat sdfor = new SimpleDateFormat("yyyy");
            String m = sdf.format(riqi);
            int i = Integer.valueOf(m).intValue();
            String month = Integer.toString(i);
            String year = sdfor.format(riqi);

            String staffId;
            if(row.getCell(1)!=null) {
                row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                if (row.getCell(1).getStringCellValue() != null) {
                    staffId = row.getCell(1).getStringCellValue();
                } else {
                    System.out.println("导入失败(第" + (r + 1) + "行,未输入工号)");
                    count2++;
                    continue;
                }
            }else {
                System.out.println("导入失败(第" + (r + 1) + "行,未输入工号)");
                count2++;
                continue;
            }

            String sbtime;
            if(row.getCell(2)==null ||row.getCell(2).getDateCellValue()==null){
                sbtime = null;
            }else{
                Date date1 = row.getCell(2).getDateCellValue();
                SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
                sbtime = sdf1.format(date1);
            }

            String xbtime ;
            if(row.getCell(3)==null || row.getCell(3).getDateCellValue()==null){
                xbtime = null;
            }else{
                Date date2 = row.getCell(3).getDateCellValue();
                SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
                xbtime = sdf2.format(date2);
            }

            String statu = judgeStatu(riqi,sbtime,xbtime);

            record = new Record();
            monthStatu = new MonthStatu();

            record.setStaffId(staffId);
            record.setRiqi(riqi);
            record.setSbtime(sbtime);
            record.setXbtime(xbtime);
            record.setStatu(statu);

            monthStatu.setStaffId(staffId);
            monthStatu.setYear(year);
            monthStatu.setMonth(month);
            Integer monStatu = 0;
            monthStatu.setStatu(monStatu);

            recordss.add(record);
            monthStatus.add(monthStatu);
        }

//      第二次导入数据库
        for (Record r : recordss) {
            String staffid = r.getStaffId();
            Date riqi = r.getRiqi();
            int cnt = recordMapper.selectByIdAndRiqi(staffid,riqi);
            if (cnt == 0) {
                recordMapper.addRecord(r);
            } else if(r.getSbtime()!=null && r.getXbtime()!=null && cnt!=0){
                recordMapper.updateByIdAndRiqi(r);
            } else{
                continue;
            }
        }
        for(MonthStatu ms:monthStatus){
            String staffid = ms.getStaffId();
            String year = ms.getYear();
            String month = ms.getMonth();
            int cnt = monthMapper.selectByIdAndMonth(staffid,year,month);
            if (cnt == 0) {
                monthMapper.addStatu(ms);
            } else{
                monthMapper.updateStatu(ms);
            }
        }

//      判断员工的第一次打卡日期、所有记录的最后一次打卡日期
//      判断工作日下员工有没有考勤记录 若无，则状态默认为旷工（如请假需由工作人员修改状态）
        for(Staff staff:staffService.selectStaffs()) {
            List<Record> record1 = selectById(staff.getUsername());
            List<Record> recordList = selectRecords();
            Date date1 = judgeMinDateById(record1);
            if (date1 == null) {
                continue;
            } else {
                java.sql.Date minDate = new java.sql.Date(date1.getTime());
                Date date2 = judgeMaxDate(recordList);
                java.sql.Date maxDate = new java.sql.Date(date2.getTime());
                for (OfficeDate officeDate : calendarService.getAllOfficeDates()) {
                    int i = 0;
                    if (officeDate.getStatus() == 1 && !officeDate.getDate().before(minDate) && !officeDate.getDate().after(maxDate)) {
                        for (Record r : record1) {
                            java.sql.Date sqlDate = new java.sql.Date(r.getRiqi().getTime());
                            if (sqlDate.equals(officeDate.getDate())) {
                                i++;//如有记录，i=1
                            }
                        }
//                    Date date = new Date();
                        if (i == 0) {
                            record = new Record();
                            record.setStaffId(staff.getUsername());
                            record.setRiqi(officeDate.getDate());
                            record.setSbtime(null);
                            record.setXbtime(null);
                            record.setStatu("旷工");
                            recordsss.add(record);
                        }
                    }
                }
            }
        }

//      再次插入记录
        for (Record r : recordsss) {
            String staffid = r.getStaffId();
            Date riqi = r.getRiqi();
            int cnt = recordMapper.selectByIdAndRiqi(staffid,riqi);
            if (cnt == 0) {
                recordMapper.addRecord(r);
            } else {
                recordMapper.updateByIdAndRiqi(r);
            }
        }
        System.out.println(count2+"条记录导入失败");
        return notNull;
    }
}

