package com.practice.management.controller;


import com.practice.management.dto.RoleDTO;
import com.practice.management.dto.UserDTO;
import com.practice.management.service.RoleService;
import com.practice.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/authority")
public class AuthorityController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

//    @RequestMapping("/getAllUsers")
//    public Response<UserDTO> getAllUsers() {
//        try{
//            UserDTO dto = userService.getUserByUserName(username);
//            return new Response<UserDTO>(dto);
//        }
//        catch (Exception e){
//            Response<UserDTO> r = new Response<UserDTO>();
//            r.setErrMsg(e.getMessage());
//            return r;
//        }
//    }

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


    @RequestMapping("/editUser")
    public Response<UserDTO> editUser(Integer staffId, String username, String password, String name, String department) {
        Response<UserDTO> r = new Response<>();
        try{
            UserDTO dto = new UserDTO();
            dto.setDepartment(department);
            dto.setId(staffId);
            dto.setUsername(username);
            dto.setPassword(password);
            dto.setName(name);
            if(userService.updateUser(dto)){
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

    @RequestMapping("/changePermissionsOfRole")
    public Response changePermissionsOfRole(@RequestParam(value = "roleId")Integer roleId,@RequestParam(value = "permissions") List<Integer> permissions) {
        Response r = new Response();
        try{
            roleService.clearPermissions(roleId);
            for (Integer permissionId: permissions) {
                roleService.addPermissionToRole(roleId, permissionId);
            }
        }
        catch (Exception e){
            r.setErrMsg(e.getMessage());
        }
        return r;
    }


    @RequestMapping("/changeRolesOfUser")
    public Response editRoleOfUser(@RequestParam(value = "userId")Integer userId,@RequestParam(value = "roles") List<Integer> roles) {
        Response r = new Response();
        try{
            userService.clearRoles(userId);
            for (int roleId: roles) {
                userService.addRoleToUser(userId, roleId);
            }
        }
        catch (Exception e){
            r.setErrMsg(e.getMessage());
        }
        return r;
    }

}
