package com.projectalpha.projectalpha.controller;

import com.projectalpha.projectalpha.dto.ErrorResponse;
import com.projectalpha.projectalpha.dto.UserRequestDTO;
import com.projectalpha.projectalpha.dto.UserResponseDTO;
import com.projectalpha.projectalpha.service.UserServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.OK;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserServices userServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private UserRequestDTO mockRequestDTO() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUserName("test");
        userRequestDTO.setPassword("test@123");
        return userRequestDTO;
    }

    private UserResponseDTO mockResponseDTO() {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setUserName("response-test");
        userResponseDTO.setEmailId("response-test@email.com");
        return userResponseDTO;
    }

    @Test
    void createUser_Success() {
        UserRequestDTO userRequestDTO = mockRequestDTO();
        UserResponseDTO userResponseDTO = mockResponseDTO();

        when(userServices.registerUser(userRequestDTO)).thenReturn(userResponseDTO);
        ResponseEntity<?> response = userController.createUser(userRequestDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(OK,response.getStatusCode());
        assertEquals(userResponseDTO, response.getBody());
        verify(userServices,times(1)).registerUser(userRequestDTO);
    }

    @Test
    void createUser_EmailAlreadyExist() {
        UserRequestDTO userRequestDTO = mockRequestDTO();

        when(userServices.registerUser(userRequestDTO)).thenThrow(new ResponseStatusException(HttpStatus.CONFLICT,"Email already exist"));
        ResponseEntity<?> response = userController.createUser(userRequestDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
        verify(userServices,times(1)).registerUser(userRequestDTO);
    }

    @Test
    void createUser_InternalServerError() {
        UserRequestDTO userRequestDTO = mockRequestDTO();

        when(userServices.registerUser(userRequestDTO)).thenThrow(new RuntimeException("Internal Server Error"));
        ResponseEntity<?> response = userController.createUser(userRequestDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
        verify(userServices,times(1)).registerUser(userRequestDTO);
    }
}