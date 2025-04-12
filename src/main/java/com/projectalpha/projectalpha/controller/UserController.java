package com.projectalpha.projectalpha.controller;


import com.projectalpha.projectalpha.customException.DuplicateSkuException;
import com.projectalpha.projectalpha.dto.ErrorResponse;
import com.projectalpha.projectalpha.entity.InventoryEntity;
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

    @PostMapping("/user")
    public ResponseEntity<?> createInventory(@RequestBody InventoryEntity inventoryEntity) {
        return null;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getBySku(@PathVariable String sku){
        return null;
    }
}
