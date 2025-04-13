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
import org.springframework.web.server.ResponseStatusException;

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

    @Test
    void registerUser_EmailAlreadyExists_ThrowsException() {
        UserRequestDTO request = getSampleRequestDTO();

        when(userRepository.existsByEmailId(request.getEmailId())).thenReturn(true);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> userServices.registerUser(request));

        assertEquals("409 CONFLICT \"Email already in use\"", ex.getMessage());
        verify(userRepository).existsByEmailId(request.getEmailId());
        verify(userRepository, never()).save(any());
        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    void registerUser_InternalError_ThrowsException() {
        UserRequestDTO request = getSampleRequestDTO();

        when(userRepository.existsByEmailId(request.getEmailId())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenThrow(new RuntimeException("Encoding failed"));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> userServices.registerUser(request));

        assertEquals("Encoding failed", ex.getMessage());
        verify(userRepository).existsByEmailId(request.getEmailId());
        verify(passwordEncoder).encode(any());
    }

}