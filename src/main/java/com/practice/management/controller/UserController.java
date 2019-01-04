package com.practice.management.controller;


import com.practice.management.dto.RoleDTO;
import com.practice.management.dto.UserDTO;
import com.practice.management.service.RoleService;
import com.practice.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/getUserInfoByName")
    public Response<UserDTO> getUserInfo(String username) {
        try{
            UserDTO dto = userService.getUserByUserName(username);
            return new Response<UserDTO>(dto);
        }
        catch (Exception e){
            Response<UserDTO> r = new Response<UserDTO>();
            r.setErrMsg(e.getMessage());
            return r;
        }
    }

    @RequestMapping("/registerUser")
    public Response<UserDTO> registerUser(Integer staffId, String username, String password, String name, String department) {
        Response<UserDTO> r = new Response<>();
        try{
            UserDTO dto = new UserDTO();
            dto.setDepartment(department);
            dto.setId(staffId);
            dto.setUsername(username);
            dto.setPassword(password);
            dto.setName(name);
            if(userService.createUser(dto)){
                return new Response<>(dto);
            }
            else{
                r.setErrMsg("数据库中创建User失败");
                return r;
            }
        }
        catch (Exception e){
            r.setErrMsg(e.getMessage());
            return r;
        }
    }

    @RequestMapping("/addPermissionToRole")
    public Response<UserDTO> addPermissionToRole(Integer staffId, String username, String password, String name, String department) {
        Response<UserDTO> r = new Response<>();
        try{
            UserDTO dto = new UserDTO();
            dto.setDepartment(department);
            dto.setId(staffId);
            dto.setUsername(username);
            dto.setPassword(password);
            dto.setName(name);
            if(userService.createUser(dto)){
                return new Response<>(dto);
            }
            else{
                r.setErrMsg("数据库中创建User失败");
                return r;
            }
        }
        catch (Exception e){
            r.setErrMsg(e.getMessage());
            return r;
        }
    }


    @RequestMapping("/changeRolesOfUser")
    public Response editRoleOfUser(Integer userId, List<Integer> roles) {
        try{
            UserDTO user = userService.getUserById(userId);
            for (int roleId: roles) {
                RoleDTO role = roleService.getRoleById(roleId);
                userService.
            }

        }
        catch (Exception e){
            r.setErrMsg(e.getMessage());
            return r;
        }
    }

}
