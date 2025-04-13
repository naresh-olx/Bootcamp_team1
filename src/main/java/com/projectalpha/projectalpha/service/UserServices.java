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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServices {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public UserResponseDTO saveUser(UserRequestDTO userDTO) {
        try{

            boolean userExists = userRepository.existsById(userDTO.getEmailId());

            if(!userExists){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User doesn't exist with ID: " + userDTO.getEmailId());
            }

            UserEntity newUser = new UserEntity();
            newUser.setUserName(userDTO.getUserName());
            newUser.setEmailId(userDTO.getEmailId());
            newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            UserEntity createdUser =  userRepository.save(newUser);

            UserResponseDTO saveUser = new UserResponseDTO();
            saveUser.setEmailId(createdUser.getEmailId());
            saveUser.setUserName(createdUser.getUserName());
            saveUser.setUserId(createdUser.getUserId());

            return saveUser;

        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User doesn't exist with ID: " + userDTO);
        }

    }

    public UserResponseDTO getUserById(String userId) {
        return null;
    }

    public UserResponseDTO addNewUser(@Valid UserRequestDTO userDTO) {

        if(userRepository.existsByEmailId(userDTO.getEmailId())) {
            throw new DuplicateSkuException("Email already in use");
        }

        UserEntity userEntity = UserMapper.toEntity(userDTO);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        UserEntity saved = userRepository.save(userEntity);
        return UserMapper.toResponse(saved);
    }
}
