package com.projectalpha.projectalpha.controller;

import com.projectalpha.projectalpha.customException.DuplicateSkuException;
import com.projectalpha.projectalpha.dto.ErrorResponse;
import com.projectalpha.projectalpha.dto.UpdateDTO;
import com.projectalpha.projectalpha.entity.InventoryEntity;
import com.projectalpha.projectalpha.service.InventoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/V1")
public class InventoryController {

    @Autowired
    private InventoryServices inventoryServices;

    @GetMapping("/health-ok")
    public String HealthOk() {
        return "Health is OK !! 👌🏻😎";
    }

    @PostMapping("/inventory")
    public ResponseEntity<?> createInventory(@RequestBody InventoryEntity inventoryEntity) {
        try {
            InventoryEntity savedItem = inventoryServices.saveInventory(inventoryEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
        } catch (DuplicateSkuException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(HttpStatus.CONFLICT, e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @GetMapping("/inventories")
    public ResponseEntity<?> getAllInventory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        try {
            Page<InventoryEntity> inventories = inventoryServices.getAllInventories(page, size);
            return ResponseEntity.status(HttpStatus.OK).body(inventories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch inventories: " + e.getMessage());
        }
    }

    @GetMapping("/{sku}")
    public ResponseEntity<?> getBySku(@PathVariable String sku) {
        try {
            InventoryEntity item = inventoryServices.getInventoryBySku(sku);
            return ResponseEntity.ok(item);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponse(e.getStatusCode(), e.getBody().getDetail()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch Item: " + e.getMessage());
        }
    }

    @PutMapping("/{sku}")
    public ResponseEntity<?> update(@PathVariable String sku, @RequestBody UpdateDTO updateDTO) {
        try {
            InventoryEntity updatedItem = inventoryServices.updateInventoryItem(sku , updateDTO);
            return ResponseEntity.status(HttpStatus.OK).body(updatedItem);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponse(e.getStatusCode(), e.getBody().getDetail()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @DeleteMapping("/{sku}")
    public ResponseEntity<?> delete(@PathVariable String sku) {
        try {
            InventoryEntity deletedItem = inventoryServices.deleteInventoryItem(sku, null);
            return ResponseEntity.status(HttpStatus.OK).body(deletedItem);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponse(e.getStatusCode(), e.getBody().getDetail()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }



}
