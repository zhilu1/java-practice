package com.practice.management.domain;

public class Staff {
    private String username;
    private String name;
    private String department;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Staff(String username, String name, String department) {
        this.username = username;
        this.name = name;
        this.department = department;
    }
}
