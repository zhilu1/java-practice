package com.practice.management.domain;

import java.sql.Date;
import java.sql.Time;

public class OfficeDate {
    private Integer id;

    private Date date;

    /*
    * status: 该日期的状态
    * 0: 默认， 非工作日
    * 1： 普通工作日
    * */
    private Integer status;
    private Time startTime;
    private Time endTime;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
}
