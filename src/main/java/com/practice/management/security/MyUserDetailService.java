package com.practice.management.security;

import com.practice.management.dao.SysPermissionMapper;
import com.practice.management.dao.SysUserMapper;
import com.practice.management.domain.SysPermission;
import com.practice.management.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// from https://www.jianshu.com/p/246e4fec1469
@Service
public class MyUserDetailService implements UserDetailsService { //自定义UserDetailsService 接口

    @Autowired
    SysUserMapper userDao;
    @Autowired
    SysPermissionMapper permissionDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //返回 user和user拥有的权限功能 by username
    @Override
    public UserDetails loadUserByUsername(String username) {
        SysUser user = userDao.getByUserName(username);
        if (user != null) {
            List<SysPermission> permissions = permissionDao.getByUserId(user.getId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList <>();
            for (SysPermission permission : permissions) {
                if (permission != null && permission.getName()!=null) {

                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getName());
                    //1：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行权限验证时会使用GrantedAuthority 对象。
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            return new User(user.getUsername(), passwordEncoder.encode(user.getPassword()), grantedAuthorities);
        } else {
            throw new UsernameNotFoundException("user: " + username + " do not exist!");
        }
    }

}
