package com.practice.management.dto;

import com.practice.management.domain.SysRole;

import java.io.Serializable;
import java.util.List;

public class UserDTO implements Serializable {
    private Integer id;
    private String username; // 工号作为用户名
    private String password;
    private String name;
    private String department;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

}
