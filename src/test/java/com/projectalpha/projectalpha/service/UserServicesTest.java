package com.projectalpha.projectalpha.service;

import com.projectalpha.projectalpha.dto.UserRequestDTO;
import com.projectalpha.projectalpha.dto.UserResponseDTO;
import com.projectalpha.projectalpha.entity.UserEntity;
import com.projectalpha.projectalpha.mapper.UserMapper;
import com.projectalpha.projectalpha.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServicesTest {

    @InjectMocks
    private UserServices userServices;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private UserRequestDTO getSampleRequestDTO() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmailId("emailId@email.com");
        userRequestDTO.setPassword("password");
        return userRequestDTO;
    }

    private UserEntity getSampleEntity() {
        return UserEntity.builder().userName("yash").emailId("yash@123").build();
    }

    @Test
    void registerUser_Success() {
        UserRequestDTO request = getSampleRequestDTO();
        UserEntity entityToSave = UserMapper.toEntity(request);
        UserEntity savedEntity = getSampleEntity();

        when(userRepository.existsByEmailId(request.getEmailId())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded_password");
        when(userRepository.save(any(UserEntity.class))).thenReturn(savedEntity);

        UserResponseDTO response = userServices.registerUser(request);

        assertNotNull(response);
        verify(userRepository).existsByEmailId(request.getEmailId());
        verify(userRepository).save(any(UserEntity.class));
        verify(passwordEncoder).encode(request.getPassword());
    }

}