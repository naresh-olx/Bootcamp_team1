package com.projectalpha.projectalpha.controller;

import com.projectalpha.projectalpha.customException.DuplicateSkuException;
import com.projectalpha.projectalpha.entity.InventoryEntity;
import com.projectalpha.projectalpha.service.InventoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/V1")
public class InventoryController {

    @Autowired
    InventoryServices inventoryServices;

    @GetMapping("/health-ok")
    public String HealthOk() {
        return "Health is OK !! 👌🏻😎";
    }

    @PostMapping("/inventory/{userId}")
    public ResponseEntity<?> createInventory(@RequestBody InventoryEntity inventoryEntity, @PathVariable UUID userId) {
        try {
            InventoryEntity savedItem = inventoryServices.saveInventoryItem(inventoryEntity, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
        } catch (DuplicateSkuException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create inventory item: " + e.getMessage());
        }
    }
}
