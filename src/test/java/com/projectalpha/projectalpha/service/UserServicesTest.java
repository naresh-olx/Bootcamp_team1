package com.projectalpha.projectalpha.service;

import com.projectalpha.projectalpha.dto.UserLoginDTO;
import com.projectalpha.projectalpha.dto.UserRequestDTO;
import com.projectalpha.projectalpha.dto.UserResponseDTO;
import com.projectalpha.projectalpha.entity.UserEntity;
import com.projectalpha.projectalpha.mapper.UserMapper;
import com.projectalpha.projectalpha.repository.UserRepository;
import com.projectalpha.projectalpha.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    UserDetailsService userDetailsService;

    @Mock
    private JwtUtil jwtUtil;

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

    private UserLoginDTO getValidLoginDTO() {
        UserLoginDTO dto = new UserLoginDTO();
        dto.setEmailId("yash@example.com");
        dto.setPassword("password123");
        return dto;
    }

    @Test
    void loginUser_UserDoesNotExist_ThrowsBadRequest() {
        UserLoginDTO loginDTO = getValidLoginDTO();

        when(userRepository.existsByEmailId(loginDTO.getEmailId())).thenReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> userServices.loginUser(loginDTO));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("User doesn't exist with EmailId: yash@example.com", exception.getReason());
    }

    @Test
    void loginUser_IncorrectPassword() {
        UserLoginDTO loginDTO = getValidLoginDTO();

        when(userRepository.existsByEmailId(loginDTO.getEmailId())).thenReturn(true);
        doThrow(new BadCredentialsException("Bad credentials"))
                .when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> userServices.loginUser(loginDTO));

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
        assertEquals("Incorrect password", exception.getReason());
    }

}