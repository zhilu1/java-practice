package com.practice.management.controller;


import com.practice.management.domain.SysRole;
import com.practice.management.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/getAllRoles")
    public ModelAndView getAllRoles() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("roleManage/roles");
        List<SysRole> roles = roleService.getAll();
        mv.addObject("roles",  roles);
        mv.addObject("errorMsg", "");
        return mv;
    }


    @RequestMapping("/editRolePage")
    public ModelAndView editRolePage(Integer roleId) {
        try{
            SysRole role = roleService.getRoleById(roleId);
            ModelAndView mv = new ModelAndView();
            mv.setViewName("roleManage/editRole");
            mv.addObject("role",  role);
            return mv;
        }
        catch (Exception e){
            ModelAndView mv = new ModelAndView();
            mv.setViewName("redirect:getAllRoles");
            mv.addObject("errorMsg", e.getMessage());
            return mv;
        }

    }
    @RequestMapping(value="/deleteRole")
    public ModelAndView deleteRole(Integer roleId) {
        roleService.deleteRoleById(roleId);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:getAllRoles");
        return mv;
    }

    @RequestMapping(value="/addRole")
    public ModelAndView addRole() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("roleManage/registerRole");
        mv.addObject("errorMsg", "");
        return mv;
    }



}
