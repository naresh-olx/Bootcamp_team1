package com.projectalpha.projectalpha.service;

import com.projectalpha.projectalpha.dto.UserRequestDTO;
import com.projectalpha.projectalpha.dto.UserResponseDTO;

import com.projectalpha.projectalpha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    public UserResponseDTO saveUser(UserRequestDTO userDTO) {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        return null;
    }

    public UserResponseDTO getUserById(String userId) {
        return null;
    }
}
