package com.practice.management.controller;

import com.practice.management.dao.RecordMapper;
import com.practice.management.domain.MonthStatu;
import com.practice.management.domain.Record;
import com.practice.management.domain.Staff;
import com.practice.management.domain.SysRole;
import com.practice.management.domain.forms.UserForm;
import com.practice.management.service.MonthService;
import com.practice.management.service.RecordService;
import com.practice.management.service.StaffService;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class RecordController {

    @Autowired
    private RecordService recordService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private MonthService monthService;

    @RequestMapping("/index")
    public String showRecord(Model model) {
        List<Record> records = recordService.selectRecords();
        model.addAttribute("records", records);
        return "jsp/index";
    }

    @RequestMapping(value = "/selectRecordByIdAndDate")
    public String selectedRecords(Model model1,String staffId,String year,String month,String check){
        if(check != null){
            List<Staff> staffs = staffService.selectStaffs();
            List<Record> records = recordService.selectByDayInAMonth(year,month);
            List<MonthStatu> monthStatus = monthService.selectByMonth(year,month);
            model1.addAttribute("staff",staffs);
            model1.addAttribute("record",records);
            model1.addAttribute("monthStatus",monthStatus);
        }else{
            Staff selectedStaff = staffService.selectById(staffId);
            List<Staff> staff = new ArrayList<>();
            staff.add(selectedStaff);
            List<Record> records = recordService.selectByIdAndDayInAMonth(staffId,year,month);
            List<MonthStatu> monthStatus = monthService.selectByMonth(year,month);
            model1.addAttribute("staff",staff);
            model1.addAttribute("record",records);
            model1.addAttribute("monthStatus",monthStatus);
        }
        return "jsp/index";
    }

    @RequestMapping(value = "/selectRecordByDate")
    public String selectedRecords(Model model1,String year,String month){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        String staffId = userDetails.getUsername();
        Staff selectedStaff = staffService.selectById(staffId);
        List<Staff> staff = new ArrayList<>();
        staff.add(selectedStaff);
        List<Record> records = recordService.selectByIdAndDayInAMonth(staffId,year,month);
        MonthStatu monthStatu = monthService.selectByIdAndMonth(staffId,year,month);
        model1.addAttribute("staff",staff);
        model1.addAttribute("record",records);
        model1.addAttribute("monthStatu",monthStatu);
        return "jsp/index";
    }

    @ResponseBody
    @RequestMapping(value = "/changeStatu")
    public Response updateStatu(String staffId,String riqi,String statu){
        Response r = new Response();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date ri = null;
        try {
            ri = new java.sql.Date(sdf.parse(riqi).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Record record1= recordService.selectByIdAndRiqi(staffId,ri);
        Record record = new Record();
        record.setStaffId(record1.getStaffId());
        record.setRiqi(record1.getRiqi());
        record.setSbtime(record1.getSbtime());
        record.setXbtime(record1.getXbtime());
        record.setStatu(statu);
        recordService.updateStatu(record);
        return r;
    }

    @RequestMapping(value = "/export")
    @ResponseBody
    public void export(HttpServletResponse response,String year,String month) throws IOException {
        List<Record> records = recordService.selectByDayInAMonth(year,month);
        List<Staff> staffs = staffService.selectStaffs();

        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet("获取excel测试表格");

        HSSFRow row1 = null;
        HSSFRow row2 = null;
        HSSFRow row3 = null;
        HSSFRow row4 = null;
        HSSFRow row5 = null;
        for(int i = 0; i < staffs.size(); i++){
            Staff staff = staffs.get(i);
            int rowNum = i * 5;
            row1 = sheet.createRow(rowNum);
            row1.setHeight((short) (22.50 * 20));//设置行高
            row1.createCell(0).setCellValue("工号:");
            row1.createCell(1).setCellValue(staff.getUsername());
            row1.createCell(2).setCellValue("姓名");
            row1.createCell(3).setCellValue(staff.getName());
            row1.createCell(4).setCellValue("部门");
            row1.createCell(5).setCellValue(staff.getDepartment());
            row2 = sheet.createRow(rowNum + 1);
            row2.createCell(0).setCellValue("日期:");
            row3 = sheet.createRow(rowNum + 2);
            row3.createCell(0).setCellValue("上班时间:");
            row4 = sheet.createRow(rowNum + 3);
            row4.createCell(0).setCellValue("下班时间:");
            row5 = sheet.createRow(rowNum + 4);
            row5.createCell(0).setCellValue("状态:");
            int count=1;
            for(int j = 0; j < records.size(); j++){
                    Record record = records.get(j);
                    if(staff.getUsername().equals(record.getStaffId())) {
                            Date date = record.getRiqi();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            String riqi = sdf.format(date);
                            row2.createCell(count).setCellValue(riqi);
                            row3.createCell(count).setCellValue(record.getSbtime());
                            row4.createCell(count).setCellValue(record.getXbtime());
                            row5.createCell(count).setCellValue(record.getStatu());
                            count ++;
                    }
                }
        }
        sheet.setDefaultRowHeight((short) (16.5 * 20));
        //列宽自适应
        for (int i = 0; i <= 13; i++) {
            sheet.autoSizeColumn(i);
        }
        response.setContentType("application/vnd.ms-excel;charset=utf-8");

        OutputStream os = response.getOutputStream();
        response.setHeader("Content-disposition", "attachment;filename=record.xls");//默认Excel名称
        wb.write(os);
        os.flush();
        os.close();

    }

    @RequestMapping(value = "/import")
    public String exImport(@RequestParam(value = "filename")MultipartFile file, HttpSession session) {

        boolean a = false;

        String fileName = file.getOriginalFilename();

        try {
            a = recordService.batchImport(fileName, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:index";
    }


    private ModelAndView recordAssist(Record record, ModelAndView mv, String errMsg){
        List<Record> roles = recordService.selectRecords();
        mv.addObject("records", roles);
        mv.addObject("record",  record);
        mv.addObject("errorMsg", errMsg);
        return mv;
    }

}