package com.practice.management.service.impl;

import com.github.pagehelper.util.StringUtil;
import com.practice.management.dao.SysPermissionMapper;
import com.practice.management.dao.SysRoleMapper;
import com.practice.management.dao.SysUserMapper;
import com.practice.management.domain.SysPermission;
import com.practice.management.domain.SysRole;
import com.practice.management.domain.SysUser;
import com.practice.management.domain.forms.UserForm;
import com.practice.management.service.RoleService;
import com.practice.management.service.UserService;
import org.assertj.core.util.Preconditions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    SysUserMapper userDao;

    @Autowired
    SysRoleMapper roleDao;

    @Autowired
    SysPermissionMapper permissionDao;

    @Autowired
    RoleService roleService;

    @Override
    public SysUser getUserByUserName(String userName) {
        SysUser user = userDao.getByUserName(userName);
        if(user != null){
            return user;
        }
        else{
            throw new IllegalArgumentException("用户名不存在");
        }
    }

    @Override
    public SysUser getUserById(Integer id) {
        SysUser user = userDao.getById(id);
        if(user != null){
            return user;
        }
        else{
            throw new IllegalArgumentException("用户ID不存在");
        }
    }

    @Override
    public List<SysUser> getAll() {
        List<SysUser> users = userDao.getAll();
        return users;
    }

    @Override
    public boolean updateUser(SysUser user) {
        Preconditions.checkNotNull(user, "入参为空");
        Preconditions.checkArgument(!StringUtil.isEmpty(user.getUsername()), "账户为空");
        Preconditions.checkArgument(!StringUtil.isEmpty(user.getName()), "用户姓名为空");
        Preconditions.checkArgument(!StringUtil.isEmpty(user.getPassword()), "用户密码为空");
        Preconditions.checkArgument(!StringUtil.isEmpty(user.getDepartment()), "用户所属部门为空");
        Preconditions.checkArgument(user.getRoles().size() >= 1, "用户至少需要一个角色");
        Preconditions.checkNotNull(userDao.getById(user.getId()), "用户id不存在");
        Preconditions.checkNotNull(userDao.getByUserName(user.getUsername()), "用户名不存在");
//        SysUser oldUser = userDao.getById(user.getId()); 复制非空

        if(userDao.updateUser(user) >= 0){
            userDao.clearAllRolesOfUser(user.getId());
            for (SysRole role: user.getRoles()) {
                userDao.addRoleToUser(role.getId(), user.getId());
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean createUser(SysUser user) {
        Preconditions.checkNotNull(user, "入参为空");
        Preconditions.checkArgument(!StringUtil.isEmpty(user.getUsername()), "账户为空");
        Preconditions.checkArgument(!StringUtil.isEmpty(user.getName()), "用户姓名为空");
        Preconditions.checkArgument(!StringUtil.isEmpty(user.getPassword()), "用户密码为空");
        Preconditions.checkArgument(!StringUtil.isEmpty(user.getDepartment()), "用户所属部门为空");
        Preconditions.checkArgument(user.getRoles().size() >= 1, "用户至少需要一个角色");
        Preconditions.checkArgument(userDao.getByUserName(user.getUsername()) == null, "账户名已存在");
        int success = userDao.createUser(user);
        if(success > 0){
            SysUser newUser = userDao.getByUserName(user.getUsername());
            if(newUser != null){
                for (SysRole role: user.getRoles()) {
                    userDao.addRoleToUser(role.getId(), newUser.getId());
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public int deleteUserByUsername(String username) {
        SysUser user = userDao.getByUserName(username);
        Preconditions.checkNotNull(user, "用户不存在");
        if (userDao.deleteById(user.getId()) >= 0) {
            return 1;
        } else {
            return 0;
        }

    }

    @Override
    public List<SysPermission> getPermissionsByUserId(Integer id) {
        SysUser user = getUserById(id);
        List<SysPermission> permissions = permissionDao.getByUserId(id);
        return permissions;
    }

    @Override
    public int addRoleToUser(Integer userId, Integer roleId) {
        Preconditions.checkNotNull(userDao.getById(userId),"该user不存在");
        Preconditions.checkNotNull(roleDao.getById(roleId),"该role不存在");
        return userDao.addRoleToUser(roleId, userId);
    }

    @Override
    public UserForm convertUserToForm(SysUser sysUser) {
        List<Integer> roleIds = new ArrayList<>();
        UserForm userForm = new UserForm();
        for(SysRole role: sysUser.getRoles()){
            roleIds.add(role.getId());
        }
        BeanUtils.copyProperties(sysUser,userForm);
        userForm.setRoleIds(roleIds);
        return userForm;
    }

    @Override
    public SysUser convertFormToUser(UserForm form) {
        Preconditions.checkArgument(form.getPassword().equals(form.getRepassword()),"两次密码不一致");
        List<SysRole> roles = new ArrayList<>();
        SysUser sysUser = new SysUser();
        for(Integer roleId: form.getRoleIds()){
            SysRole role = roleService.getRoleById(roleId);
            roles.add(role);
        }
        BeanUtils.copyProperties(form,sysUser);
        sysUser.setRoles(roles);
        return sysUser;
    }

//    @Override
//    public void clearRoles(Integer userId) {
//        Preconditions.checkNotNull(userDao.getById(userId),"该user不存在");
//        List<SysRole> roles = roleDao.getByUserId(userId);
//        for (SysRole role: roles) {
//            userDao.removeRoleFromUser(role.getId(), userId);
//        }
//    }
}
