package com.practice.management.controller;


import com.practice.management.dto.UserDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {


    @RequestMapping("/getUserInfoByName")
    public UserDTO getUserInfo() {
        return null;
    }

}
