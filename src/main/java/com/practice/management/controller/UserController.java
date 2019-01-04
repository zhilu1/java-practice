package com.practice.management.controller;


import com.practice.management.dto.UserDTO;
import com.practice.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/getUserInfoByName")
    public Response<UserDTO> getUserInfo(String username) {
        try{
            UserDTO dto = userService.getUserByUserName(username);
            return new Response(dto);
        }
        catch (Exception e){
            Response<UserDTO> r = new Response();
            r.setErrMsg(e.getMessage());
            return r;
        }
    }

}
