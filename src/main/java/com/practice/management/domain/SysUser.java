package com.practice.management.domain;

import java.util.List;
import java.util.Objects;

public class SysUser {
    private Integer id;
    private String username; // 工号作为用户名
    private String password;
    private String name;
    private String department;
    private List<SysRole> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SysUser)) return false;
        SysUser sysUser = (SysUser) o;
        return getId().equals(sysUser.getId()) &&
                getUsername().equals(sysUser.getUsername()) &&
                getPassword().equals(sysUser.getPassword()) &&
                getName().equals(sysUser.getName()) &&
                getDepartment().equals(sysUser.getDepartment()) &&
                Objects.equals(getRoles(), sysUser.getRoles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getPassword(), getName(), getDepartment(), getRoles());
    }

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }


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
