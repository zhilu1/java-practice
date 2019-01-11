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

    @RequestMapping("/registerUser")
    public ModelAndView registerUser(@ModelAttribute UserForm userForm) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("userManage/registerUser");
        try {
            SysUser user = userService.convertFormToUser(userForm);
            userService.createUser(user);
        }
        catch (Exception e){
            return redirectAssist(userForm, mv, e.getMessage());
        }
        return  new ModelAndView("redirect:getAllUsers");
    }


    @RequestMapping(value="/addUser")
    public ModelAndView addUser() {
        ModelAndView mv = new ModelAndView();
        UserForm userForm = new UserForm();
        mv.setViewName("userManage/registerUser");
        return redirectAssist(userForm, mv, "");
    }


    /*
    * assist function for redirection to register User page
    * */
    private ModelAndView redirectAssist(UserForm userForm, ModelAndView mv, String errMsg){
        List<SysRole> roles = roleService.getAll();
        mv.addObject("allRoles", roles);
        mv.addObject("userForm",  userForm);
        mv.addObject("errorMsg", errMsg);
        return mv;
    }


    @RequestMapping("/changeUserPage")
    public ModelAndView changeUserPage(String username,  @RequestParam(value = "errMsg", defaultValue = "")String errMsg) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("userManage/editUser");
        SysUser user = userService.getUserByUserName(username);
        UserForm userForm = userService.convertUserToForm(user);
        return redirectAssist(userForm, mv, "");
    }


    @RequestMapping(value="/editUser")
    public ModelAndView editUser(@ModelAttribute UserForm userForm) {
        ModelAndView mv = new ModelAndView();
        try {
            SysUser sysUser = userService.convertFormToUser(userForm);
            userService.updateUser(sysUser);
            mv.setViewName("redirect:getAllUsers" );
        }
        catch (Exception e){
            mv.setViewName("userManage/editUser");
            return redirectAssist(userForm, mv, e.getMessage());
        }
        return mv;
    }

    @RequestMapping(value="/deleteUser")
    public ModelAndView deleteUser(String username) {
        userService.deleteUserByUsername(username);
        return  new ModelAndView("redirect:getAllUsers");
    }


}
