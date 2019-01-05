package com.practice.management.service;

import com.practice.management.dto.PermissionDTO;
import com.practice.management.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO getUserByUserName(String userName);

    UserDTO getUserById(Integer id);

    List<UserDTO> getAll();

    boolean updateUser(UserDTO user);

    boolean createUser(UserDTO user);

    int deleteUserById(Integer id);

    List<PermissionDTO> getPermissionsByUserId(Integer id);

    int addRoleToUser(Integer userId, Integer roleId);

    void clearRoles(Integer userId);
}
