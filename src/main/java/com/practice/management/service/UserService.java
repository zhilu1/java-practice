package com.practice.management.service;

import com.practice.management.dto.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserDTO getUserByUserName(String userName);

    List<UserDTO> getAll();

    UserDTO updateUser(UserDTO user);

    int deleteUserById(Integer id);
}
