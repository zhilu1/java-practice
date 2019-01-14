package com.practice.management.controller;


import com.practice.management.domain.OfficeDate;
import com.practice.management.domain.forms.CalendarForm;
import com.practice.management.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/calendar")
public class CalendarController {

    @Autowired
    CalendarService calendarService;

    @RequestMapping("/manage")
    public ModelAndView redirectCalendarPage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("calendar");
        List<OfficeDate> allDates = calendarService.getAllOfficeDates();
        mv.addObject("allDates", allDates);
        return mv;
    }

    @RequestMapping("/view")
    public ModelAndView viewOfficeTimeCalendar() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("calendar");
        return mv;
    }

    @RequestMapping("/edit")
    public ModelAndView changeCalendarByDate(@ModelAttribute CalendarForm calendarForm) {
        ModelAndView mv = new ModelAndView();
        try {
            List<OfficeDate> list = calendarService.convertFormIntoListDates(calendarForm);
            calendarService.setOfficeDates(list);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        mv.setViewName("redirect:manage");
        return mv;
    }
}
