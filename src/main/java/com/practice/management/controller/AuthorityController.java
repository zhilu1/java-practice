package com.practice.management.controller;


import com.practice.management.domain.SysRole;
import com.practice.management.domain.SysUser;
import com.practice.management.service.RoleService;
import com.practice.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/authority")
public class AuthorityController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/getAllUsers")
    public ModelAndView getAllUsers() {
        ModelAndView mv = new ModelAndView();
//        try{
            mv.setViewName("users");
            List<SysUser> dtos= userService.getAll();
            mv.addObject("users",  dtos);
            return mv;
//        }
//        catch (Exception e){
//            mv.addObject("error", e.getMessage());
//            return mv;
//        }
    }
    @RequestMapping("/getAllRoles")
    public Response<List<SysRole>> getAllRoles() {
        Response r = new Response();
        try{
            List<SysRole> roles = roleService.getAll();
            r.setWrapper(roles);
        }
        catch (Exception e){
            r.setErrMsg(e.getMessage());
        }
        return r;
    }
    @RequestMapping("/getUserInfoByName")
    public ModelAndView getUserInfo(String username) {
        ModelAndView mv = new ModelAndView();
//        try{
            mv.setViewName("editUser");
            SysUser user = userService.getUserByUserName(username);
            List<SysRole> roles = roleService.getAll();
            mv.addObject("allRoles", roles);
            mv.addObject("sysUser",  user);
            return mv;
//        }
//        catch (Exception e){
//            mv.addObject("error", e);
//            return mv;
//        }
    }

    @RequestMapping("/registerUser")
    public Response<SysUser> registerUser(Integer staffId, String username, String password, String name, String department) {
        Response<SysUser> r = new Response<>();
        try{
            SysUser dto = new SysUser();
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

    @RequestMapping(value="/editUser")
    public ModelAndView editUser( @ModelAttribute SysUser sysUser) {
        userService.updateUser(sysUser);
        return getAllUsers();
    }

//    @RequestMapping(value="/editUser", method= RequestMethod.POST)
//    public Response<SysUser> editUser(Integer staffId, String username, String password, String name, String department) {
//        Response<SysUser> r = new Response<>();
//        try{
//            SysUser dto = new SysUser();
//            dto.setDepartment(department);
//            dto.setId(staffId);
//            dto.setUsername(username);
//            dto.setPassword(password);
//            dto.setName(name);
//            if(userService.updateUser(dto)){
//                return new Response<>(dto);
//            }
//            else{
//                r.setErrMsg("数据库中创建User失败");
//                return r;
//            }
//        }
//        catch (Exception e){
//            r.setErrMsg(e.getMessage());
//            return r;
//        }
//    }

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
