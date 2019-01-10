package com.practice.management.controller;


import com.practice.management.domain.SysPermission;
import com.practice.management.domain.SysRole;
import com.practice.management.service.PermissionService;
import com.practice.management.service.RoleService;
import com.practice.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/getAllRoles")
    public ModelAndView getAllRoles() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("roles");
        List<SysRole> roles = roleService.getAll();
        mv.addObject("roles",  roles);
        mv.addObject("errorMsg", "");
        return mv;
    }

    @RequestMapping("/getAllPermissions")
    @ResponseBody
    public  Response<List<SysPermission>> getAllPermissions() {
        Response<List<SysPermission>> r = new Response<>();
        try{
            List<SysPermission> ps = permissionService.getAll();
            r.setWrapper(ps);
        }
        catch (Exception e){
            r.setErrMsg(e.getMessage());
        }
        return r;
    }


    @RequestMapping("/getPermissionsOfRole")
    @ResponseBody
    public Response<List<SysPermission>> getPermissionsOfRole(@RequestParam(value = "roleId")Integer roleId) {
        Response<List<SysPermission>> r = new Response<>();
        try{
            List<SysPermission> ps = roleService.getPermissionsById(roleId);
            r.setWrapper(ps);
        }
        catch (Exception e){
            r.setErrMsg(e.getMessage());
        }
        return r;
    }

    @RequestMapping("/editRolePage")
    public ModelAndView editRolePage(Integer roleId) {
        try{
            SysRole role = roleService.getRoleById(roleId);
            ModelAndView mv = new ModelAndView();
            mv.setViewName("editRole");
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
        mv.setViewName("registerRole");
        mv.addObject("errorMsg", "");
        return mv;
    }

    @RequestMapping(value = "/changePermissionsOfRole", method = RequestMethod.POST)
    @ResponseBody
    public Response changePermissionsOfRole( @RequestParam(value = "roleId")Integer roleId, @RequestParam(value = "permissions") List<Integer> permissions) {
        Response r = new Response();
        try{
            roleService.clearPermissions(roleId);
            for (Integer permissionId: permissions) {
                roleService.addPermissionToRole(roleId, permissionId);
            }
            return r;
        }
        catch (Exception e){
            r.setErrMsg(e.getMessage());
            return r;
        }
    }

    @RequestMapping(value = "/registerRole", method = RequestMethod.POST)
    public Response registerRole( @RequestParam(value = "name", required = false)String name, @RequestParam(value = "permissions", required = false ) List<Integer> permissions) {
        Response r = new Response();
        if(permissions == null){
            r.setErrMsg("角色至少需要一个权限 ");
        }
        else if(name.isEmpty() || name == null){
            r.setErrMsg("角色名不得为空 ");
        }
        else{
            try {
                Integer roleId = roleService.createRole(name, permissions);
                for (Integer permissionId : permissions) {
                    roleService.addPermissionToRole(roleId, permissionId);
                }
                return r;
            } catch (Exception e) {
                r.setErrMsg(e.getMessage());
                return r;
            }
        }
        return r;
    }

}
