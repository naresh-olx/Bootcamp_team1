package com.projectalpha.projectalpha.controller;

import com.projectalpha.projectalpha.entity.InventoryEntity;
import com.projectalpha.projectalpha.service.InventoryServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.OK;

class InventoryControllerTest {

    @InjectMocks
    private InventoryController inventoryController;

    @Mock
    private InventoryServices inventoryServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBySku_Success() {
        String sku = "123-xyz";
        InventoryEntity mockItem = InventoryEntity.builder().sku(sku).vin(12345L).make("Toyota").build();

        when(inventoryServices.getInventoryBySku(sku)).thenReturn(mockItem);

        ResponseEntity<?> responseEntity = inventoryController.getBySku(sku);

        assertEquals(OK , responseEntity.getStatusCode());
    }
}