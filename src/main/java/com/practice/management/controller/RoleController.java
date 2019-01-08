package com.practice.management.controller;


import com.practice.management.domain.SysPermission;
import com.practice.management.domain.SysRole;
import com.practice.management.domain.SysUser;
import com.practice.management.service.RoleService;
import com.practice.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/getAllRoles")
    public ModelAndView getAllRoles() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("roles");
        List<SysRole> roles = roleService.getAll();
        mv.addObject("roles",  roles);
        return mv;
    }

    @RequestMapping("/getPermissionsOfRole")
    @ResponseBody
    public Response<List<SysPermission>> getPermissionsOfRole(@RequestParam(value = "roleId")Integer roleId) {
        Response<List<SysPermission>> r = new Response();
        try{
            List<SysPermission> ps = roleService.getPermissionsById(roleId);
            r.setWrapper(ps);
        }
        catch (Exception e){
            r.setErrMsg(e.getMessage());
        }
        return r;
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

}
