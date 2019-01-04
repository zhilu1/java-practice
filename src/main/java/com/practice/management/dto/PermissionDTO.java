package com.practice.management.dto;

import java.io.Serializable;

public class PermissionDTO implements Serializable {
    private Integer id;
    private String name; //权限名
    private String description;
    private String url; //权限匹配的可访问url
    private Integer pid; // 父权限的id

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
}
