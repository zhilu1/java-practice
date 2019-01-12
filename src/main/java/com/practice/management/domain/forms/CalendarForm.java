package com.practice.management.domain.forms;

import java.util.ArrayList;
import java.util.List;

public class CalendarForm {
    // 所改变日期范围
    private String startDate;
    private String endDate;

    private String startTime; // 上班时间
    private String endTime; // 下班时间
    private Integer status; // 日期类型
    private Boolean ignoHoliday; // 节假日是否调整 （周末不属于节假日）
    private Boolean ignoWeekends;// 周末是否调整

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getIgnoHoliday() {
        return ignoHoliday;
    }

    public void setIgnoHoliday(Boolean ignoHoliday) {
        this.ignoHoliday = ignoHoliday;
    }

    public Boolean getIgnoWeekends() {
        return ignoWeekends;
    }

    public void setIgnoWeekends(Boolean ignoWeekends) {
        this.ignoWeekends = ignoWeekends;
    }
}
