package com.practice.management.controller;

import com.practice.management.dao.MonthMapper;
import com.practice.management.domain.MonthStatu;
import com.practice.management.domain.Record;
import com.practice.management.service.MonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
public class MonthStatuController {
    @Autowired
    private MonthMapper monthMapper;

    @ResponseBody
    @RequestMapping(value = "/changeMonthStatu")
    public Response updateStatu(String year,String month){
        Response r = new Response();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        String staffId = userDetails.getUsername();
        MonthStatu monthStatu = new MonthStatu();
        monthStatu.setStaffId(staffId);
        monthStatu.setYear(year);
        monthStatu.setMonth(month);
        Integer statu = 1;
        monthStatu.setStatu(statu);
        monthMapper.updateStatu(monthStatu);
        return r;
    }
}
