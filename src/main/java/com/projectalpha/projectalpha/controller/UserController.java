package com.projectalpha.projectalpha.controller;


import com.projectalpha.projectalpha.customException.DuplicateSkuException;
import com.projectalpha.projectalpha.dto.ErrorResponse;
import com.projectalpha.projectalpha.dto.UserRequestDTO;
import com.projectalpha.projectalpha.dto.UserResponseDTO;
import com.projectalpha.projectalpha.entity.InventoryEntity;
import com.projectalpha.projectalpha.entity.UserEntity;
import com.projectalpha.projectalpha.service.InventoryServices;
import com.projectalpha.projectalpha.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/V1/users/")
public class UserController {
    @Autowired
    private UserServices userServices;

    @GetMapping("/")
    public String healthCheck() {
        return "Health Check";
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody UserRequestDTO userDTO) {
        try{
            UserResponseDTO saveUser =  userServices.saveUser(userDTO);
            return ResponseEntity.status(HttpStatus.OK).body(saveUser);
        }catch (DuplicateSkuException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(HttpStatus.CONFLICT, e.getMessage()));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable String userId){
        return null;
    }
}
