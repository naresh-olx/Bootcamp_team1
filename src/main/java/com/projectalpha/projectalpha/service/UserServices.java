package com.projectalpha.projectalpha.service;

import com.projectalpha.projectalpha.customException.DuplicateSkuException;
import com.projectalpha.projectalpha.dto.UserRequestDTO;
import com.projectalpha.projectalpha.dto.UserResponseDTO;
import com.projectalpha.projectalpha.entity.InventoryEntity;
import com.projectalpha.projectalpha.entity.UserEntity;
import com.projectalpha.projectalpha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    public UserResponseDTO saveUser(UserRequestDTO userDTO) {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
//        userRepository.insert(userRequestDTO);
        return null;
    }

    public UserResponseDTO getUserById(String userId) {
        return null;
    }
}
