package com.practice.management.controller;


import com.practice.management.domain.SysRole;
import com.practice.management.domain.SysUser;
import com.practice.management.domain.forms.UserForm;
import com.practice.management.service.RoleService;
import com.practice.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
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
            mv.setViewName("userManage/users");
            List<SysUser> dtos= userService.getAll();
            mv.addObject("users",  dtos);
            return mv;
    }

    @RequestMapping("/getUserInfoByName")
    public ModelAndView getUserInfo(String username) {
        ModelAndView mv = new ModelAndView();
            mv.setViewName("userManage/editUser");
            SysUser user = userService.getUserByUserName(username);
            List<SysRole> roles = roleService.getAll();
            UserForm userForm = userService.convertUserToForm(user);
            mv.addObject("allRoles", roles);
            mv.addObject("userForm",  userForm);
            return mv;
    }

    @RequestMapping("/registerUser")
    public ModelAndView registerUser(@ModelAttribute UserForm userForm) {
        SysUser user = userService.convertFormToUser(userForm);
        try {
            userService.createUser(user);
        }
        catch (Exception e){
            ModelAndView mv = new ModelAndView();
            mv.setViewName("userManage/registerUser");
            List<SysRole> roles = roleService.getAll();
            mv.addObject("allRoles", roles);
            mv.addObject("userForm", userForm);
            mv.addObject("errorMsg", e.getMessage());
            return mv;
        }
        return  new ModelAndView("redirect:getAllUsers");
    }

    @RequestMapping(value="/addUser")
    public ModelAndView addUser() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("userManage/registerUser");
        List<SysRole> roles = roleService.getAll();
        UserForm userForm = new UserForm();
        mv.addObject("allRoles", roles);
        mv.addObject("userForm",  userForm);
        mv.addObject("errorMsg", "");
        return mv;
    }

    @RequestMapping(value="/editUser")
    public ModelAndView editUser(@ModelAttribute UserForm userForm) {
        SysUser sysUser = userService.convertFormToUser(userForm);
        userService.updateUser(sysUser);
        return  new ModelAndView("redirect:getAllUsers");
    }

    @RequestMapping(value="/deleteUser")
    public ModelAndView deleteUser(String username) {
        userService.deleteUserByUsername(username);
        return  new ModelAndView("redirect:getAllUsers");
    }


}
