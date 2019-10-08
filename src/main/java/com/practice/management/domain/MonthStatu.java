package com.practice.management.domain;

public class MonthStatu {
    private String staffId;
    private String year;
    private String month;
    private Integer statu;

    public MonthStatu(String staffId, String year,String month, Integer statu) {
        this.staffId = staffId;
        this.year = year;
        this.month = month;
        this.statu = statu;
    }

    public MonthStatu() {
        super();
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getStatu() {
        return statu;
    }

    public void setStatu(Integer statu) {
        this.statu = statu;
    }
}
