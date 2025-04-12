package com.projectalpha.projectalpha.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InventoryEntityTest {
    @Test
    void testInventoryEntity() {
        
        void testInventoryEntityAllArgsConstructor() {
            LocalDateTime createdAt = LocalDateTime.now();

            LocalDateTime now = LocalDateTime.now();
            InventoryEntity inventoryEntity = new InventoryEntity(
                    "SKU1234",
                    "Car",
                    "CREATED",
                    "Mumbai",
                    1234567890L,
                    "Toyota",
                    "Corolla",
                    "2020",
                    "XLE",
                    200000.0,
                    250000.0,
                    "admin",
                    now,
                    "admin",
                    now
            );
        }
    }

}