package com.practice.management.controller;

import com.practice.management.domain.SysPermission;
import com.practice.management.service.PermissionService;
import com.practice.management.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleRestController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/getAllPermissions")
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

    @RequestMapping(value = "/changeRole", method = RequestMethod.POST)
    public Response changeRole( @RequestParam(value = "roleId")Integer roleId, @RequestParam(value = "name", required = false)String name, @RequestParam(value = "permissions", required = false ) List<Integer> permissions) {
        Response r = new Response();
        if (permissions == null) {
            r.setErrMsg("角色至少需要一个权限 ");
            return r;
        }
        try{
            roleService.updateRole(roleId, name);
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
    public Response registerRole(@RequestParam(value = "name", required = false)String name, @RequestParam(value = "permissions", required = false ) List<Integer> permissions) {
        Response r = new Response();
        if (permissions == null) {
            r.setErrMsg("角色至少需要一个权限 ");
            return r;
        }
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
}
