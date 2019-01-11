package com.practice.management.controller;

import com.practice.management.domain.Record;
import com.practice.management.service.RecordService;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class RecordController {

    @Autowired
    private RecordService recordService;

    @RequestMapping("/index")
    public String showRecord(Model model) {
        List<Record> records = recordService.selectRecords();
        model.addAttribute("record", records);
        return "jsp/index";
    }


    @RequestMapping(value = "/selectRecordByIdAndDate")
    public String seclectedRecords(Model model1,String id,String year,String month){
        List<Record> records = recordService.selectByIdAndDate(id,year,month);
        model1.addAttribute("record",records);
        return "jsp/index";
    }


    @RequestMapping(value = "/export")
    @ResponseBody
    public void export(HttpServletResponse response) throws IOException {
        List<Record> records = recordService.selectRecords();

        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet("获取excel测试表格");

        HSSFRow row = null;


        row = sheet.createRow(0);
        row.setHeight((short) (22.50 * 20));//设置行高
        row.createCell(0).setCellValue("日期");//为第一个单元格设值
        row.createCell(1).setCellValue("工号");//为第二个单元格设值
        row.createCell(2).setCellValue("上班时间");//为第三个单元格设值
        row.createCell(3).setCellValue("下班时间");

        for (int i = 0; i < records.size(); i++) {
            row = sheet.createRow(i + 1);
            Record record = records.get(i);
            Date date = record.getRiqi();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String riqi = sdf.format(date);
            row.createCell(0).setCellValue(riqi);
            row.createCell(1).setCellValue(record.getStaffId());
            row.createCell(2).setCellValue(record.getSbtime());
            row.createCell(3).setCellValue(record.getXbtime());
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

}