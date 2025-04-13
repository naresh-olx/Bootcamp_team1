package com.projectalpha.projectalpha.service;

import com.projectalpha.projectalpha.customException.DuplicateSkuException;
import com.projectalpha.projectalpha.dto.UserRequestDTO;
import com.projectalpha.projectalpha.dto.UserResponseDTO;

import com.projectalpha.projectalpha.entity.UserEntity;
import com.projectalpha.projectalpha.mapper.UserMapper;
import com.projectalpha.projectalpha.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class UserServices {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public UserResponseDTO getUserById(String userId) {
        return null;
    }

    public UserResponseDTO registerUser(@Valid UserRequestDTO userDTO) {

        if(userRepository.existsByEmailId(userDTO.getEmailId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
        }

        UserEntity userEntity = UserMapper.toEntity(userDTO);
        userEntity.setCreatedAt(LocalDateTime.now());
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        UserEntity saved = userRepository.save(userEntity);
        return UserMapper.toResponse(saved);
    }

    public String loginUser(@Valid UserRequestDTO userDTO) {

        if(userRepository.existsByEmailId(userDTO.getEmailId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User doesn't exist with EmailId: " + userDTO.getEmailId());
        }

        return null;
    }
}
