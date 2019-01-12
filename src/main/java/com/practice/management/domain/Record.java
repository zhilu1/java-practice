package com.practice.management.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Record  implements Serializable {

    private static final long serialVersionUID = 5176731501090561016L;
    private String staffId;

    private Date riqi;

    private String sbtime;

    private String xbtime;

    public Record(String staffId, Date riqi, String sbtime, String xbtime) {
        this.staffId = staffId;
        this.riqi = riqi;
        this.sbtime = sbtime;
        this.xbtime = xbtime;
    }

    public Record() {
        super();
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public Date getRiqi() {
        return riqi;
    }

    public void setRiqi(Date riqi) {
        this.riqi = riqi;
    }

    public String getSbtime() {
        return sbtime;
    }

    public void setSbtime(String sbtime) {
        this.sbtime = sbtime;
    }

    public String getXbtime() {
        return xbtime;
    }

    public void setXbtime(String xbtime) {
        this.xbtime = xbtime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return staffId.equals(record.staffId) &&
                riqi.equals(record.riqi) &&
                Objects.equals(sbtime, record.sbtime) &&
                Objects.equals(xbtime, record.xbtime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(staffId, riqi, sbtime, xbtime);
    }
}