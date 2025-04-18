package com.projectalpha.controller;


import com.projectalpha.dto.ErrorResponse;
import com.projectalpha.dto.UserLoginDTO;
import com.projectalpha.dto.UserRequestDTO;
import com.projectalpha.dto.UserResponseDTO;
import com.projectalpha.service.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/V1/user")
public class UserController {
    @Autowired
    private UserServices userServices;

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequestDTO userDTO) {
        try{
            UserResponseDTO saveUser =  userServices.registerUser(userDTO);
            return ResponseEntity.status(HttpStatus.OK).body(saveUser);
        }catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponse(e.getStatusCode(), e.getBody().getDetail()));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO user) {
        try{
            String JWT_Token =  userServices.loginUser(user);
            return ResponseEntity.status(HttpStatus.OK).body(JWT_Token);
        }catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponse(e.getStatusCode(), e.getBody().getDetail()));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

}
