package com.practice.management.service;

import com.practice.management.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO getUserByUserName(String userName);

    List<UserDTO> getAll();

    UserDTO updateUser(UserDTO user);

    int deleteUserById(Integer id);
}
